package com.dasgrau.uebung6_2

import android.content.ContentValues
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun buttonClickedInsert(v: View) {
        val title = findViewById<EditText>(R.id.editTextTextTitle).text.toString()
        val acro = findViewById<EditText>(R.id.editTextTextAcronym).text.toString()
        val lectuer = findViewById<EditText>(R.id.editTextTextLecturer).text.toString()
        val grade = findViewById<EditText>(R.id.editTextGrade).text.toString().toInt()
        var values = ContentValues()
        values.put(CourseDBOpenHelper.COL_TITLE, title)
        values.put(CourseDBOpenHelper.COL_ACRONYM, acro)
        values.put(CourseDBOpenHelper.COL_LECTURER, lectuer)
        values.put(CourseDBOpenHelper.COL_GRADE, grade)
        contentResolver.insert(CourseContentProvider.URI, values)
    }

    fun buttonClickedList(v: View) {
        startActivity(Intent(this, ActivityList::class.java))
    }
}