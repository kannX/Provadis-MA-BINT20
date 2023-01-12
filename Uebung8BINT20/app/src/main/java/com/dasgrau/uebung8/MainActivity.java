package com.dasgrau.uebung8;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.toString();

    private TextView textView = null;
    private ImageView imageView = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = findViewById(R.id.textView);
        imageView = findViewById(R.id.imageView);

        ActivityResultLauncher<String> mGetContentImage = registerForActivityResult(new ActivityResultContracts.GetContent(),
                new ActivityResultCallback<Uri>() {
                    @Override
                    public void onActivityResult(Uri uri) {
                        textView.setText(uri.toString());
                        imageView.setImageURI(uri);
                    }
                });
        findViewById(R.id.buttonImage).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mGetContentImage.launch("image/*");
            }
        });

        ActivityResultLauncher<Void> mGetContentContact = registerForActivityResult(
                new ActivityResultContracts.PickContact(),
                new ActivityResultCallback<Uri>() {
                    @Override
                    public void onActivityResult(Uri uri) {
                        if(uri == null) {
                            Log.e(TAG, "No contact selected!");
                            return;
                        }
                        Log.i(TAG, uri.toString());
                        String[] projectionFields = new String[]{ContactsContract.Contacts.DISPLAY_NAME};
                        Cursor cursor = getContentResolver().query(uri, projectionFields,
                                null, null);
                        try {
                            if(!cursor.moveToFirst())
                                return;
                            String displayName = cursor.getString(0);
                            textView.setText(displayName);
                        } finally {
                            cursor.close();
                        }
                    }
                });
        findViewById(R.id.buttonContact).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mGetContentContact.launch(null);
            }
        });
    }

    public void onClickButton1(View v) {
        Log.i(TAG, "Button clicked");
    }
}