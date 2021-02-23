package com.example.androidnotepad

import android.content.ContentValues
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ShareCompat
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.androidnotepad.db.AppDatabase
import com.example.androidnotepad.db.Note

class MainActivity : AppCompatActivity() {
    lateinit var btnAdd: Button
    lateinit var recyclerNote: RecyclerView
    lateinit var noteAdapter: NoteAdapter
    lateinit var appDatabase: AppDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        appDatabase = AppDatabase.getAppDatabase(this)

        noteAdapter = NoteAdapter()
        btnAdd = findViewById(R.id.button_add)
        recyclerNote = findViewById(R.id.recycler_notes)
        recyclerNote.layoutManager = LinearLayoutManager(this)
        recyclerNote.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))
        recyclerNote.adapter = noteAdapter

        btnAdd.setOnClickListener {
            val values = ContentValues().apply {
                put("title", "haha")
                put("description", "a description")
                put("viewCount", 0)
            }
            contentResolver.insert(ExampleProvider.Companion.Note.CONTENT_URI, values)
            fetch()
        }
        fetch()


        findViewById<Button>(R.id.button_open).setOnClickListener {
            val shareIntent =
                Intent(Intent.ACTION_SEND)
            shareIntent.setDataAndType(ExampleProvider.Companion.Note.CONTENT_URI, "image/*")
            shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
            startActivity(shareIntent)
        }
    }

    fun fetch() {
        val cursor = contentResolver.query(
            ExampleProvider.Companion.Note.CONTENT_URI,
            null, null, null, null
        )

        cursor?.apply {
            val idIndex: Int = getColumnIndex("id")
            val titleIndex: Int = getColumnIndex("title")
            val list = mutableListOf<Note>()
            while (moveToNext()) {
                val id = getInt(idIndex)
                val title = getString(titleIndex)
                val description = getString(2)
                val viewCount = getInt(3)
                list.add(Note(id, title, description, viewCount))
            }
            noteAdapter.dataSet = list
            noteAdapter.notifyDataSetChanged()
        }
    }
}