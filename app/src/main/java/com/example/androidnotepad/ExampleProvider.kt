package com.example.androidnotepad;

import android.content.ContentProvider
import android.content.ContentValues
import android.content.UriMatcher
import android.database.Cursor
import android.database.MatrixCursor
import android.net.Uri
import com.example.androidnotepad.db.AppDatabase
import com.example.androidnotepad.db.Note
import com.example.androidnotepad.db.NoteDao
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class ExampleProvider : ContentProvider() {
    companion object {
        private val AUTHORITY = "com.example.androidnotepad.user_notepad"

        object Note {
            val CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/notes")
            val CONTENT_URI_2 = Uri.parse("content://" + AUTHORITY + "/notes2")
        }
    }

    private val sUriMatcher = UriMatcher(UriMatcher.NO_MATCH).apply {
        addURI(AUTHORITY, "notes", 1)
        addURI(AUTHORITY, "notes/#", 2)
        addURI(AUTHORITY, "adifferenttable/#", 3)
    }

    private lateinit var appDatabase: AppDatabase
    private lateinit var noteDao: NoteDao

    override fun onCreate(): Boolean {
        appDatabase = AppDatabase.getAppDatabase(context!!)
        noteDao = appDatabase.noteDao()
        return true
    }

    override fun query(
        uri: Uri,
        projection: Array<out String>?,
        selection: String?,
        selectionArgs: Array<out String>?,
        sortOrder: String?
    ): Cursor? {
        when (sUriMatcher.match(uri)) {
            1 -> {
                val matrix = MatrixCursor(arrayOf("id", "title", "description", "viewCount"))
                runBlocking {
                    val notes = appDatabase.noteDao().getAllNotes()
                    notes.forEach {
                        matrix.addRow(arrayOf(it.id, it.title, it.description, it.viewCount))
                    }
                }
                return matrix
            }
            2 -> {

            }
        }
        return null
    }

    override fun getType(uri: Uri): String? {
        TODO("Not yet implemented")
    }

    override fun insert(uri: Uri, values: ContentValues?): Uri? {
        if (values == null) {
            return null
        }
        when (sUriMatcher.match(uri)) {
            1 -> {
                val title = values.getAsString("title")
                val description = values.getAsString("description")
                val viewCount = values.getAsInteger("viewCount")
                GlobalScope.launch {
                    noteDao.insert(Note(title, description, viewCount))
                }
            }

            3 -> {

            }
        }
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