package com.dasgrau.uebung17

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

/**
 * Basiert auf Thomas Künneth: Android 11 . Das Praxisbuch für App-Entwickler, Kapitel 6
 */
class MainActivity: AppCompatActivity(), CoroutineScope {

    companion object {
        private val TAG = MainActivity::class.toString()
    }

    private lateinit var textView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        textView = findViewById(R.id.textView)
        // Vergleich: einfachen Thread erstellen (ohne Coroutinen)
        /*
        launch {
            var i = 0
            while(i < 1000) {
                delay(3000)
                Log.d(TAG, "${i++}")
            }
        }
         */
    }

    override fun onPause() {
        super.onPause()
        // bricht CoroutineContext mit allen Jobs (und Kindern) ab
        cancel(null)
    }

    fun onButtonClicked(v: View) {
        Log.i(TAG, "Button clicked")
        // Neue Coroutine erstellen und starten
        GlobalScope.launch {
            pause((1 + Math.random() * 10).toLong())
        }
    }

    private suspend fun pause(seconds: Long) {
        Log.i(TAG, "Entering pause")
        // im UI-Thread ausführen
        withContext(Dispatchers.Main) {
            addText("Waiting $seconds seconds")
        }
        delay(1000 * seconds)
        withContext(Dispatchers.Main) {
            // Aufgabe in UI-Thread
            addText("... done waiting")
        }
    }

    private fun addText(s: String) {
        var currentText = textView.text
        if(currentText.isNotEmpty())
            currentText = "\n$currentText"
        textView.text = getString(R.string.template, s, currentText)
    }

    override val coroutineContext: CoroutineContext
        get() = SupervisorJob() + Dispatchers.IO
}