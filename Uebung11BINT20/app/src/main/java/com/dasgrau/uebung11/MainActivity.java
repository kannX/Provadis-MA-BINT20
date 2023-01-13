package com.dasgrau.uebung11;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.toString();
    protected static final String EXTRA_PERSON_NAME = "personName";
    private EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editText = findViewById(R.id.editTextTextPersonName);
    }

    public void onClickButtonService(View v) throws InterruptedException {
        // Option 1: Aktuellen Thread mit langer Aktivität blockieren - schlecht
        //Thread.sleep(30000);

        // Option 2: Thread im Hintergrund. Problem: Zugriff auf UI (editText) nicht möglich - Fehler
        /*
        new Thread(){
            @Override
            public void run() {
                try {
                    //editText.setText("Thread läuft");
                    Thread.sleep(10000);
                    Log.i(TAG, "Thread finished");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }.start();
         */

        // Option 3: Hintergrunddienst mit Looper, ServiceHandler, ...
        String personName = editText.getText().toString();
        Log.i(TAG, String.format("Got from EditText: %s", personName));
        Intent serviceIntent = new Intent(MainActivity.this, ExampleService.class);
        serviceIntent.putExtra(EXTRA_PERSON_NAME, personName);
        startService(serviceIntent);
    }
}