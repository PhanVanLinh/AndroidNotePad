package com.example.androidnotepad.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Note::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun noteDao(): NoteDao

    companion object {
        fun getAppDatabase(context: Context): AppDatabase {
            return Room.databaseBuilder(
                    context,
                    AppDatabase::class.java, "database-name"
            ).build()
        }
    }
}