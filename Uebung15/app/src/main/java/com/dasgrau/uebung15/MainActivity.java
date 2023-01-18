package com.dasgrau.uebung15;

import android.app.ProgressDialog;
import android.net.Uri;
import android.net.http.HttpResponseCache;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

/**
 * Partially based on https://code.tutsplus.com/tutorials/android-from-scratch-using-rest-apis--cms-27117
 */
public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.toString();
    private HttpResponseCache httpCache = null;
    private TextView textView;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = findViewById(R.id.textView);
        try {
            httpCache = HttpResponseCache.install(getCacheDir(), 10000000L);
        } catch (IOException e) {
            Log.e(TAG, "Failed to create HttpResponseCache", e);
        }
    }

    public void onClickButtonCall(View v) {
        Log.d(TAG, "starting GithubTask");
        new GithubTask().execute();
    }

    private class GithubTask extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // display a progress dialog for good user experiance
            progressDialog = new ProgressDialog(MainActivity.this);
            progressDialog.setMessage("Calling github...");
            progressDialog.setCancelable(false);
            progressDialog.show();
        }

        @Override
        protected String doInBackground(String... strings) {
            HttpsURLConnection githubConnection = null;
            try {
                URL githubUrl = new URL("https://api.github.com/users");
                githubConnection = (HttpsURLConnection) githubUrl.openConnection();
                githubConnection.setRequestProperty("User-Agent", "com.dasgrau.uebung15");
                githubConnection.setRequestProperty("Accept", "application/vnd.github.v3+json");
                githubConnection.setRequestProperty("Contact-Me", "black@dasgrau.com");

                int responseCode = githubConnection.getResponseCode();
                if(responseCode != 200) {
                    Log.e(TAG, String.format("Received response code %d, excpected 200!", responseCode));
                    return null;
                }

                BufferedReader reader = new BufferedReader(new InputStreamReader(githubConnection.getInputStream()));
                StringBuilder stringBuilder = new StringBuilder();
                for(String line; (line = reader.readLine()) != null;)
                    stringBuilder.append(line).append('\n');

                Log.i(TAG, String.format("Received a response of %d characters length", stringBuilder.length()));
                return stringBuilder.toString();
            } catch(Exception e) {
                e.printStackTrace();
                return null;
            } finally {
                if(githubConnection != null)
                    githubConnection.disconnect();
                if(httpCache != null)
                    Log.d(TAG, String.format("Cache hits: %d", httpCache.getHitCount()));
            }
        }

        @Override
        protected void onPostExecute(String response) {
            if(progressDialog != null)
                progressDialog.dismiss();
            if(response == null) {
                Log.e(TAG, "null response in onPostExecute");
                return;
            }
            try {
                JSONArray jsonArrayUser = new JSONArray(response);
                JSONObject jsonObjectUser1 =jsonArrayUser.getJSONObject(0);
                String id = jsonObjectUser1.getString("id");
                String name = jsonObjectUser1.getString("login");
                String printableString = String.format("UserID: %s, Login name: %s", id, name);
                textView.setText(printableString);
            } catch (
                    JSONException e) {
                e.printStackTrace();
            }
        }
    } // Ende GithubTask
} // Ende MainActivity