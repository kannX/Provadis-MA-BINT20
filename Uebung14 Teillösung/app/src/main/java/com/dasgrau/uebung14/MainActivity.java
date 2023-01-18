package com.dasgrau.uebung14;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.dasgrau.calculator.AddRequest;
import com.dasgrau.calculator.CalculatorGrpc;

import java.lang.ref.WeakReference;
import java.util.concurrent.TimeUnit;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.StatusRuntimeException;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.toString();
    private EditText editTextX;
    private EditText editTextY;
    private TextView textViewSum;
    private Button buttonAdd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editTextX = findViewById(R.id.editTextNumberDecimalX);
        editTextY = findViewById(R.id.editTextNumberDecimalY);
        textViewSum = findViewById(R.id.textViewResult);
        buttonAdd = findViewById(R.id.buttonAdd);
    }

    public void calculate(View v) {
        buttonAdd.setEnabled(false);
        textViewSum.setText("Calculating ...");
        new CalculatorTask(this).execute(Double.parseDouble(editTextX.getText().toString()), Double.parseDouble(editTextY.getText().toString()));
    }

    private static final class CalculatorTask extends AsyncTask<Double, Void, Double> {

        private static final int STATUS_CODE_OK = 0;
        private static final int STATUS_CODE_ERROR = 1;
        private static final String host = "10.0.2.2";
        private static final int port = 8080;
        private final WeakReference<MainActivity> activityRef;
        private ManagedChannel channel;
        private int statusCode;

        private CalculatorTask(MainActivity activity) {
            this.activityRef = new WeakReference<MainActivity>(activity);
        }

        @Override
        protected Double doInBackground(Double... doubles) {
            statusCode = STATUS_CODE_OK;
            channel = ManagedChannelBuilder.forAddress(host, port).usePlaintext().build();
            CalculatorGrpc.CalculatorBlockingStub stub = CalculatorGrpc.newBlockingStub(channel);
            try {
                AddRequest request = AddRequest.newBuilder().setX(doubles[0]).setY(doubles[1]).build();
                return stub.addNumbers(request).getResult();
            } catch (StatusRuntimeException e) {
                Log.e(MainActivity.TAG, "gRPC call failed");
                statusCode = STATUS_CODE_ERROR;
                return new Double(0);
            }
        }

        @Override
        protected void onPostExecute(Double aDouble) {
            try {
                channel.shutdown().awaitTermination(1, TimeUnit.SECONDS);
            } catch (InterruptedException e) {
            }
            MainActivity activity = activityRef.get();
            if (activity == null) return;
            if (statusCode == STATUS_CODE_ERROR)
                activity.textViewSum.setText("Error");
            else
                activity.textViewSum.setText(String.valueOf(aDouble));
            activity.buttonAdd.setEnabled(true);
        }
    }
}