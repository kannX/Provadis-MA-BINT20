package com.dasgrau.uebung6_2

import android.content.ContentValues
import android.content.Context
import android.content.UriMatcher
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteException
import android.database.sqlite.SQLiteOpenHelper
import android.net.Uri
import android.util.Log

class CourseDBOpenHelper(context: Context): SQLiteOpenHelper(context, DB_NAME, null,
    DB_VERSION) {

    companion object {
        const val AUTH = "com.dasgrau.uebung6_2.CourseContentProvider"
        const val DB_NAME = "provadis_courses.db"
        const val DB_VERSION = 1
        const val TABLE_COURSES = "courses"
        const val COL_ID = "_id"
        const val COL_TITLE = "title"
        const val COL_ACRONYM = "acronym"
        const val COL_LECTURER = "lecturer"
        const val COL_GRADE = "grade"

        var TAG = CourseDBOpenHelper::class.toString()
    }

    override fun onCreate(db: SQLiteDatabase?) {
        if(db is SQLiteDatabase) {
            db?.execSQL(
                "CREATE TABLE $TABLE_COURSES (" +
                        "$COL_ID INTEGER," +
                        "$COL_TITLE TEXT NOT NULL, " +
                        "$COL_ACRONYM TEXT NOT NULL UNIQUE, " +
                        "$COL_LECTURER TEXT, " +
                        "$COL_GRADE INTEGER, " +
                        "PRIMARY KEY($COL_ID AUTOINCREMENT) );"
            )
            Log.i(TAG, "DB created")
        } else
            Log.e(TAG, "Could not create DB!")
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        TODO("Not yet implemented")
    }

    fun insert(title: String, acronym: String, lecturer: String? = null, grade: Int): Uri? {
        var rowID = -1L
        try {
            val values = ContentValues()
            values.put(COL_TITLE, title)
            values.put(COL_ACRONYM, acronym)
            values.put(COL_LECTURER, lecturer)
            values.put(COL_GRADE, grade)
            rowID = writableDatabase.insert(TABLE_COURSES, null, values)
            return Uri.parse("content://$AUTH/$TABLE_COURSES/$rowID")
        } catch (e: SQLiteException) {
            Log.e(TAG, "Failed to write to DB")
            return null
        } finally {
            Log.i(TAG, "Insert into $TABLE_COURSES with rowID $rowID")
        }
    }

    fun query(
        uri: Uri,
        projection: Array<out String>?,
        selection: String?,
        selectionArgs: Array<out String>?,
        sortOrder: String?
    ): Cursor? {
        return readableDatabase.query(TABLE_COURSES, projection, selection,
            selectionArgs, "", "", sortOrder)
    }
}