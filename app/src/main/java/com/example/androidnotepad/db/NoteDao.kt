package com.example.androidnotepad.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface NoteDao {
    @Query("SELECT * FROM note")
    suspend fun getAllNotes(): List<Note>

    @Query("SELECT * FROM note WHERE id = :id")
    suspend fun getNote(id: Int): Note

    @Insert
    suspend fun insert(note: Note)
}