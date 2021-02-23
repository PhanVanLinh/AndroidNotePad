package com.example.androidnotepad.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class Note(
    var title: String,
    var description: String,
    var viewCount: Int
) {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0

    constructor(
        id: Int,
        title: String,
        description: String,
        viewCount: Int
    ) : this(title, description, viewCount) {
        this.id = id
    }
}