package com.dasgrau.uebung9;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private ActivityResultLauncher<String> mGetContentImage;
    private TextView textView;
    private Uri imageUri = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = findViewById(R.id.textViewURI);
        mGetContentImage = registerForActivityResult(new ActivityResultContracts.GetContent(),
                new ActivityResultCallback<Uri>() {
                    @Override
                    public void onActivityResult(Uri uri) {
                        imageUri = uri;
                        textView.setText(imageUri.toString());
                        ((ImageView) findViewById(R.id.imageView)).setImageURI(imageUri);
                    }
                });
    }

    public void onClickSelect(View v) {
        mGetContentImage.launch("image/*");
    }

    public void onClickShare(View v) {
        // Example from https://developer.android.com/guide/components/intents-filters#ForceChooser

        Intent sendIntent = new Intent(Intent.ACTION_SEND, imageUri);
        // Always use string resources for UI text.
        // This says something like "Share this photo with"
        String title = getResources().getString(R.string.chooser_title);
        // Create intent to show the chooser dialog
        Intent chooser = Intent.createChooser(sendIntent, title);

        // Verify the original intent will resolve to at least one activity
        if (sendIntent.resolveActivity(getPackageManager()) != null) {
            startActivity(chooser);
        } else
            Toast.makeText(getApplicationContext(), "No suitable app present!", Toast.LENGTH_SHORT).show();
    }
}