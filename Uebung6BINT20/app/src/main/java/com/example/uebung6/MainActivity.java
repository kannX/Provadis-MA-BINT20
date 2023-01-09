package com.example.uebung6;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.AlarmClock;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void button1(View v) {
        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.provadis-hochschule.de")));
    }

    public void buttonRing(View v) {
        /*
        Intent i = new Intent(AlarmClock.ACTION_SET_TIMER);
        i.putExtra(AlarmClock.EXTRA_LENGTH, 10);
        i.putExtra(AlarmClock.EXTRA_SKIP_UI, true);
        startActivity(i);
         */
        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("provadis://das.ist.eine.blöde.uri")));
    }
}