package com.dasgrau.uebung6_2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.CallLog
import android.widget.ListView
import androidx.cursoradapter.widget.SimpleCursorAdapter

class ActivityList : AppCompatActivity() {

    companion object {
        val TAG = ActivityList::class.toString()
        val COLUMN_NAMES = arrayOf(
            CourseDBOpenHelper.COL_ID, CourseDBOpenHelper.COL_TITLE,
            CourseDBOpenHelper.COL_ACRONYM, CourseDBOpenHelper.COL_LECTURER, CourseDBOpenHelper.COL_GRADE)

        val ADAPTER_FROM = arrayOf(CourseDBOpenHelper.COL_TITLE,
            CourseDBOpenHelper.COL_ACRONYM, CourseDBOpenHelper.COL_LECTURER, CourseDBOpenHelper.COL_GRADE)
        val ADAPTER_TO = intArrayOf(R.id.textViewTitle, R.id.textViewAcronym, R.id.textViewLecturer, R.id.textViewGrade)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list)
    }

    override fun onResume() {
        super.onResume()
        var cursor = contentResolver.query(CourseContentProvider.URI, COLUMN_NAMES,
            null, null, null)

        var adapter = SimpleCursorAdapter(applicationContext, R.layout.course_card, cursor,
            ADAPTER_FROM, ADAPTER_TO, 0)

        findViewById<ListView>(R.id.listViewCourses).adapter = adapter
    }
}