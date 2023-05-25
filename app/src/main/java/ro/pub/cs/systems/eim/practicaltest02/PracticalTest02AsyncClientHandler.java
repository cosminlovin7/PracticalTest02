package ro.pub.cs.systems.eim.practicaltest02;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class PracticalTest02AsyncClientHandler extends AsyncTask<String, Void, String> {
    private TextView clientResultTextView;
    public PracticalTest02AsyncClientHandler(final TextView clientResultTextView) {
        this.clientResultTextView = clientResultTextView;
    }

    @Override
    protected String doInBackground(String... params) {
        Log.d(Constants.TAG, Constants.CLIENT_TAG + "Connecting to server...");
        try {
            Socket connection = new Socket(params[0], Integer.parseInt(params[1]));
            Log.d(Constants.TAG, Constants.CLIENT_TAG + "Connection successful!");

            if (params[2].equalsIgnoreCase(Constants.GET_METHOD)) {
                Log.d(Constants.TAG, Constants.CLIENT_TAG + "Sending get method...");
                PrintWriter printWriter = new PrintWriter(connection.getOutputStream(), true);

                printWriter.println(Constants.GET_METHOD);
                printWriter.flush();
                printWriter.println(params[3]);
                printWriter.flush();

                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String response =  bufferedReader.readLine();

                Log.d(Constants.TAG, Constants.CLIENT_TAG + "Received response from server: " + response);

                return response;
            } else if (params[2].equalsIgnoreCase(Constants.POST_METHOD)) {
                Log.d(Constants.TAG, Constants.CLIENT_TAG + "Sending post method...");
                PrintWriter printWriter = new PrintWriter(connection.getOutputStream(), true);

                printWriter.println(Constants.POST_METHOD);
                printWriter.flush();
                printWriter.println(params[3]);
                printWriter.flush();
                printWriter.println(params[4]);
                printWriter.flush();

                Log.d(Constants.TAG, Constants.CLIENT_TAG + "Sent post method with params: " + params[3] + " " + params[4]);
            }
            connection.close();
            Log.d(Constants.TAG, Constants.CLIENT_TAG + "Connection ended.");
        } catch (IOException e) {
            Log.d(Constants.TAG, Constants.CLIENT_TAG + "Could not establish a connection...");
        }
        return null;
    }

    public void onPostExecute(String result) {
        if (result != null) {
            clientResultTextView.setText(result);
        }
    }
}
