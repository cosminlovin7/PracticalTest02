package ro.pub.cs.systems.eim.practicaltest02;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class PracticalTest02ServerFragment extends Fragment {
    private final ButtonClickListener buttonClickListener = new ButtonClickListener();
    private class ButtonClickListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            if (!serverPortEditText.getText().toString().equalsIgnoreCase("")) {
                if (serverThread == null) {
                    serverThread = new PracticalTest02ServerThread(Integer.parseInt(serverPortEditText.getText().toString()));
                    serverThread.start();
                } else {
                    Toast.makeText(getContext(), "Server is already running", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(getContext(), "Please, provide a port.", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private EditText serverPortEditText;
    private Button startButton;
    private PracticalTest02ServerThread serverThread;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_practical_test02_server, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        serverPortEditText = getActivity().findViewById(R.id.sserver_port_edittext);
        startButton = getActivity().findViewById(R.id.start_button);

        startButton.setOnClickListener(buttonClickListener);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (serverThread != null) {
            serverThread.stopServer();
        }
    }

}