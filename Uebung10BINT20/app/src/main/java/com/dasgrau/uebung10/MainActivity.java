package com.dasgrau.uebung10;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

    private final String TAG = MainActivity.class.toString();

    private TextView textView;
    private DatePickerFragment datePicker;

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        textView.setText(getString(R.string.date, dayOfMonth, month+1, year));
        Log.i(TAG, "Dialog finished");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "1 onCreate()");
        setContentView(R.layout.activity_main);
        textView = findViewById(R.id.textView);
        datePicker = new DatePickerFragment();
    }

    public void onClickButtonDialog(View v) {
        Log.i(TAG, "Opening dialog");

        // Alternative 1: DataPicker als Fraggment
        //datePicker.show(getSupportFragmentManager(), DatePickerFragment.TAG);

        // Alternative 2: quick & diryt - DaterPickerDialog so einfach wie möglich starten
        Calendar cal = Calendar.getInstance();
        new DatePickerDialog(MainActivity.this, this,
                cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DATE)).show();
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        Log.d(TAG, "onPostCreate()");
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        Log.d(TAG, "onPostResume()");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "2 onStart()");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "5 onStop()");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "6 onDestroy()");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "4 onPause()");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "3 onResume()");
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.d(TAG, "onSaveInstanceState()");
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        Log.d(TAG, "onRestoreInstanceState()");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d(TAG, "5-2 onRestart()");
    }
}