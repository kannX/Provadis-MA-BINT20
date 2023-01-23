package com.dasgrau.uebung6_2

import android.content.ContentProvider
import android.content.ContentValues
import android.content.Context
import android.content.UriMatcher
import android.database.Cursor
import android.net.Uri
import android.util.Log

class CourseContentProvider : ContentProvider() {

    companion object {
        private val TAG = CourseContentProvider::class.toString()
        val URI: Uri = Uri.parse("content://com.dasgrau.uebung6_2.CourseContentProvider/courses")
        val COURSES = 1
        val COURSES_ID = 2
        val TYPE_COURSE = "vnd.android.cursor.dir/vnd.com.dasgrau.course"
        val TYPE_ITEM_COURSE = "vnd.android.cursor.item/vnd.com.dasgrau.course"

        private val MATCHER = UriMatcher(UriMatcher.NO_MATCH)

        init {
            MATCHER.addURI(CourseDBOpenHelper.AUTH, CourseDBOpenHelper.TABLE_COURSES, COURSES)
            MATCHER.addURI(
                CourseDBOpenHelper.AUTH,
                "${CourseDBOpenHelper.TABLE_COURSES}/#",
                COURSES_ID
            )
        }
    }

    var openHelper: CourseDBOpenHelper? = null

    override fun onCreate(): Boolean {
        if (context is Context) {
            openHelper = CourseDBOpenHelper(context!!)
            return true
        } else
            return false
    }

    override fun query(
        uri: Uri,
        projection: Array<out String>?,
        selection: String?,
        selectionArgs: Array<out String>?,
        sortOrder: String?
    ): Cursor? {
        return openHelper?.query(uri, projection, selection, selectionArgs, sortOrder)
    }

    override fun getType(uri: Uri) =
        when (MATCHER.match(uri)) {
            COURSES -> TYPE_COURSE
            COURSES_ID -> TYPE_ITEM_COURSE
            else -> null
        }

    override fun insert(uri: Uri, values: ContentValues?): Uri? {
        Log.i(TAG, "Inserting ...")
        if (values != null) {
            return openHelper?.insert(
                values.getAsString(CourseDBOpenHelper.COL_TITLE),
                values.getAsString(CourseDBOpenHelper.COL_ACRONYM),
                values.getAsString(CourseDBOpenHelper.COL_LECTURER),
                values.getAsInteger(CourseDBOpenHelper.COL_GRADE)
            )
        } else
            return null
    }

    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<out String>?): Int {
        TODO("Not yet implemented")
    }

    override fun update(
        uri: Uri,
        values: ContentValues?,
        selection: String?,
        selectionArgs: Array<out String>?
    ): Int {
        TODO("Not yet implemented")
    }
}