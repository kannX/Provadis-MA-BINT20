package com.example.uebung7;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onClickButton1(View v) {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("telekom://activity2"));
        startActivity(intent);
    }
}