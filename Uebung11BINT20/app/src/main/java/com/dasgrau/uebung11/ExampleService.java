package com.dasgrau.uebung11;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.Process;
import android.widget.Toast;

/**
 * Example from https://developer.android.com/guide/components/services
 */
public class ExampleService extends Service {

    private Looper serviceLooper;
    private ServiceHandler serviceHandler;

    private final class ServiceHandler extends Handler {

        public ServiceHandler(Looper looper) {
            super(looper);
        }

        @Override
        public void handleMessage(Message msg) {
            Toast.makeText(ExampleService.this, String.format("Received bundle data %s",
                    msg.getData().getString(MainActivity.EXTRA_PERSON_NAME, "no data")),
                    Toast.LENGTH_LONG).show();
            try {Thread.sleep(30000);} catch(InterruptedException e) {}
            stopSelf(msg.arg1);
        }
    }

    @Override
    public void onCreate() {
        HandlerThread thread = new HandlerThread("ServiceStartArguments",
                Process.THREAD_PRIORITY_BACKGROUND);
        thread.start();

        serviceLooper = thread.getLooper();
        serviceHandler = new ServiceHandler(serviceLooper);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Toast.makeText(this, "service starting", Toast.LENGTH_SHORT).show();

        String personName = intent.getStringExtra(MainActivity.EXTRA_PERSON_NAME);

        Message msg = serviceHandler.obtainMessage();
        msg.arg1 = startId;
        Bundle bundle = msg.getData();
        bundle.putString(MainActivity.EXTRA_PERSON_NAME, personName);

        serviceHandler.sendMessage(msg);
        return START_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy() {
        Toast.makeText(this, "service done", Toast.LENGTH_SHORT).show();
    }
}