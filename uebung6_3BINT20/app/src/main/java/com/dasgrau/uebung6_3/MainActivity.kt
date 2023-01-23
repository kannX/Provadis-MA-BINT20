package com.dasgrau.uebung6_3

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.view.View
import java.io.File
import java.io.FileReader

/**
 * Basiert auf https://github.com/mercie-nyakio/Android-preferences
 */
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

    }

    fun onClickButtonSettings(v: View) {
        startActivity(Intent(this, SettingsActivity::class.java))
    }
}