package ro.pub.cs.systems.eim.practicaltest02;

import androidx.appcompat.app.AppCompatActivity;
import android.app.FragmentManager;

import android.app.FragmentTransaction;
import android.os.Bundle;

public class PracticalTest02MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_practical_test02_main);

        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.server_fragment, new PracticalTest02ServerFragment());
        fragmentTransaction.add(R.id.client_fragment, new PracticalTest02ClientFragment());
        fragmentTransaction.commit();
    }
}