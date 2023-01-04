package com.dasgrau.uebung2bint20;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private TextView textViewTelekom;
    private int counter = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textViewTelekom = findViewById(R.id.textViewTelekom);
        textViewTelekom.setText(getString(R.string.helloCounter, counter));

        /*
        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                counter++;
                textViewTelekom.setText(getString(R.string.helloCounter, counter));
            }
        });
         */
    }

    public void onClickButton(View v) {
        counter++;
        textViewTelekom.setText(getString(R.string.helloCounter, counter));
    }
}