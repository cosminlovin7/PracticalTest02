package ro.pub.cs.systems.eim.practicaltest02;

import android.util.Log;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class PracticalTest02ServerThread extends Thread {
    private ServerSocket serverSocket;
    private final Integer serverPort;
    private boolean isRunning;

    public PracticalTest02ServerThread(final Integer serverPort) {
        this.serverPort = serverPort;
    }

    @Override
    public void run() {
        Log.d(Constants.TAG, Constants.SERVER_TAG + "Starting server...");
        try {
            serverSocket = new ServerSocket(serverPort);
            Log.d(Constants.TAG, Constants.SERVER_TAG + "Server started successfully. Accepting connections...");
            isRunning = true;

            while(isRunning) {
                Socket clientSocket = serverSocket.accept();
                Log.d(Constants.TAG, Constants.SERVER_TAG + "New client connected! Establishing communication channel...");

                new PracticalTest02CommunicationThread(clientSocket).start();
            }
        } catch (IOException e) {
            Log.d(Constants.TAG, Constants.SERVER_TAG + "Server socket closed. Server shutting down...");
        }
    }

    public void stopServer() {
        isRunning = false;
        if (serverSocket != null) {
            try {
                serverSocket.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
