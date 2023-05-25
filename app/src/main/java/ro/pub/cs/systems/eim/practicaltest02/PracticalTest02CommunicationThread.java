package ro.pub.cs.systems.eim.practicaltest02;

import android.os.Build;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.Socket;
import java.net.URL;
import java.sql.Date;
import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.Map;
import java.util.TimeZone;

import cz.msebera.android.httpclient.HttpEntity;
import cz.msebera.android.httpclient.HttpResponse;
import cz.msebera.android.httpclient.client.HttpClient;
import cz.msebera.android.httpclient.client.methods.HttpGet;
import cz.msebera.android.httpclient.impl.client.DefaultHttpClient;

public class PracticalTest02CommunicationThread extends Thread {
    private Socket clientSocket;
    private Map<String, EntryInfo> hashMap;

    public PracticalTest02CommunicationThread(final Socket clientSocket, final Map<String, EntryInfo> hashMap) {
        this.clientSocket = clientSocket;
        this.hashMap = hashMap;
    }

    @Override
    public void run() {
        Log.d(Constants.TAG, Constants.COMM_TAG + "Communication established with the client!");
        try {
            Log.d(Constants.TAG, Constants.COMM_TAG + "Getting time from API.");
            HttpClient httpClient = new DefaultHttpClient();
            HttpGet httpGet = new HttpGet(Constants.HOST);
            HttpResponse httpResponse = httpClient.execute(httpGet);
            HttpEntity httpGetEntity = httpResponse.getEntity();

            LocalDateTime apiTime;
            LocalDateTime currentTime = null;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                currentTime = LocalDateTime.now().plusHours(3L);
            }
            if (httpGetEntity != null) {
                BufferedReader apiReader = new BufferedReader(new InputStreamReader(httpGetEntity.getContent()));

                StringBuilder apiResult = new StringBuilder();
                String line;
                while((line = apiReader.readLine()) != null) {
                    apiResult.append(line);
                }

                Log.d(Constants.TAG, Constants.COMM_TAG + "Received " + apiResult.toString());
                JSONObject jsonObject = new JSONObject(apiResult.toString());

                String datetime = jsonObject.getString("datetime");
                Log.d(Constants.TAG, Constants.COMM_TAG + "Datetime: " + datetime.substring(0, datetime.length() - 6));
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    apiTime = LocalDateTime.parse(datetime.substring(0, datetime.length() - 6));

                    Log.d(Constants.TAG, apiTime.toString());
                    Log.d(Constants.TAG, LocalDateTime.now().plusHours(3L).toString());
                }

            } else {
                Log.d(Constants.TAG, Constants.COMM_TAG + "Failed to retrieve time from API.");
                clientSocket.close();
                return;
            }

            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            String method = bufferedReader.readLine();

            if (method.equalsIgnoreCase(Constants.GET_METHOD)) {
                //todo
                String key = bufferedReader.readLine();
                EntryInfo entryInfo = null;
                String result = "none\n";
                if (hashMap.containsKey(key)) {
                    entryInfo = hashMap.get(key);

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        if (currentTime.isBefore(entryInfo.getTime().plusSeconds(Constants.expiringSeconds))) {
                            result = entryInfo.getValue() + "\n";
                        } else {
                            hashMap.remove(key);
                        }
                    }
                }

                PrintWriter printWriter = new PrintWriter(clientSocket.getOutputStream(), true);
                printWriter.println(result);
                printWriter.flush();
            } else if (method.equalsIgnoreCase(Constants.POST_METHOD)) {
                String key = bufferedReader.readLine();
                String value = bufferedReader.readLine();

                EntryInfo entryInfo = new EntryInfo(value, currentTime);
                hashMap.put(key, entryInfo);
                Log.d(Constants.TAG, Constants.COMM_TAG + "Received pair from client: " + key + ":" + value);
            }

            clientSocket.close();
            Log.d(Constants.TAG, Constants.COMM_TAG + "Communication ended!");
        } catch (IOException e) {
            Log.d(Constants.TAG, Constants.COMM_TAG + e.getMessage());
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }
}
