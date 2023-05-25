package ro.pub.cs.systems.eim.practicaltest02;

import android.util.Log;

import java.io.IOException;
import java.net.Socket;

public class PracticalTest02CommunicationThread extends Thread {
    private Socket clientSocket;

    public PracticalTest02CommunicationThread(final Socket clientSocket) {
        this.clientSocket = clientSocket;
    }

    @Override
    public void run() {
        Log.d(Constants.TAG, Constants.COMM_TAG + "Communication established with the client!");
        try {
            clientSocket.close();
            Log.d(Constants.TAG, Constants.COMM_TAG + "Communication ended!");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
