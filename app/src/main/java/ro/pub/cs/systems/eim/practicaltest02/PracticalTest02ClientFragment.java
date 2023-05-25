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
import android.widget.TextView;
import android.widget.Toast;

public class PracticalTest02ClientFragment extends Fragment {
    private final ButtonClickListener buttonClickListener = new ButtonClickListener();
    private class ButtonClickListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            int buttonId = view.getId();

            if (buttonId == R.id.get_button) {
                if (serverAddressEditText.getText().toString().equalsIgnoreCase("")) {
                    Toast.makeText(getContext(), "Please, provide a server address.", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (serverPortEditText.getText().toString().equalsIgnoreCase("")) {
                    Toast.makeText(getContext(), "Please, provide a server port.", Toast.LENGTH_SHORT).show();
                    return;
                }

                PracticalTest02AsyncClientHandler practicalTest02AsyncClientHandler = new PracticalTest02AsyncClientHandler();
                practicalTest02AsyncClientHandler.execute(
                        serverAddressEditText.getText().toString(),
                        serverPortEditText.getText().toString()
                );
            } else if (buttonId == R.id.post_button) {
                if (serverAddressEditText.getText().toString().equalsIgnoreCase("")) {
                    Toast.makeText(getContext(), "Please, provide a server address.", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (serverPortEditText.getText().toString().equalsIgnoreCase("")) {
                    Toast.makeText(getContext(), "Please, provide a server port.", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (keyEditText.getText().toString().equalsIgnoreCase("")) {
                    Toast.makeText(getContext(), "Please, provide a key.", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (valueEditText.getText().toString().equalsIgnoreCase("")) {
                    Toast.makeText(getContext(), "Please, provide a value.", Toast.LENGTH_SHORT).show();
                    return;
                }
            }
        }
    }

    private EditText serverAddressEditText;
    private EditText serverPortEditText;
    private EditText keyEditText;
    private EditText valueEditText;
    private Button getButton;
    private Button postButton;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_practical_test02_client, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        serverAddressEditText = getActivity().findViewById(R.id.cserver_address_edittext);
        serverPortEditText = getActivity().findViewById(R.id.cserver_port_edittext);
        getButton = getActivity().findViewById(R.id.get_button);
        postButton = getActivity().findViewById(R.id.post_button);

        getButton.setOnClickListener(buttonClickListener);
        postButton.setOnClickListener(buttonClickListener);
    }

}