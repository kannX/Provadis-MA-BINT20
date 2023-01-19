package com.dasgrau.uebung6_1

import android.Manifest
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.CallLog
import android.util.Log
import android.widget.ListView
import androidx.annotation.RequiresPermission
import androidx.cursoradapter.widget.SimpleCursorAdapter

class MainActivity : AppCompatActivity() {

    companion object {
        val TAG = MainActivity::class.toString()
        const val REQUEST_CODE = 42
        val CALL_LOG_COLUMNS = arrayOf(CallLog.Calls._ID, CallLog.Calls.NUMBER,
            CallLog.Calls.DURATION)

        val ADAPTER_FROM = arrayOf(CallLog.Calls.NUMBER, CallLog.Calls.DURATION)
        val ADAPTER_TO = intArrayOf(R.id.textViewNumber, R.id.textViewDuration)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

       if(checkSelfPermission(Manifest.permission.READ_CALL_LOG)
           != PackageManager.PERMISSION_GRANTED) {
           Log.i(TAG, "Permission not granted, requesting...")
           requestPermissions(arrayOf(Manifest.permission.READ_CALL_LOG), REQUEST_CODE)
       } else {
           Log.i(TAG, "Permission already granted")
           queryCalls()
       }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        Log.i(TAG, "onRequestPermissionsResult with requestCode $requestCode" +
                " and grantResults[0] $grantResults[0]")
        if(requestCode != REQUEST_CODE || grantResults[0] != PackageManager.PERMISSION_GRANTED) {
            Log.i(TAG, "...permission NOT granted")
            return
        }
        Log.i(TAG, "...permission granted")
        queryCalls()
    }

    private fun queryCalls() {
        var cursor = contentResolver.query(CallLog.Calls.CONTENT_URI, CALL_LOG_COLUMNS,
            null, null, "${CallLog.Calls.LAST_MODIFIED} DESC")

        var adapter = SimpleCursorAdapter(applicationContext, R.layout.call_card, cursor,
            ADAPTER_FROM, ADAPTER_TO, 0)

        findViewById<ListView>(R.id.listViewCalls).adapter = adapter
    }
}