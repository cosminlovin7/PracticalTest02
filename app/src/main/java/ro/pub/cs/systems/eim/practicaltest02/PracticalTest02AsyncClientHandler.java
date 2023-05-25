package ro.pub.cs.systems.eim.practicaltest02;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import java.io.IOException;
import java.net.Socket;

public class PracticalTest02AsyncClientHandler extends AsyncTask<String, Void, Void> {

    public PracticalTest02AsyncClientHandler() {

    }

    @Override
    protected Void doInBackground(String... params) {
        Log.d(Constants.TAG, Constants.CLIENT_TAG + "Connecting to server...");
        try {
            Socket connection = new Socket(params[0], Integer.parseInt(params[1]));
            Log.d(Constants.TAG, Constants.CLIENT_TAG + "Connection successful!");

            connection.close();
            Log.d(Constants.TAG, Constants.CLIENT_TAG + "Connection ended.");
        } catch (IOException e) {
            Log.d(Constants.TAG, Constants.CLIENT_TAG + "Could not establish a connection...");
        }
        return null;
    }

    public void onPostExecute(Void result) {

    }
}
