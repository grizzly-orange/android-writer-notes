package com.grizzlyorange.writernotes.data.note

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Note(
    @PrimaryKey(autoGenerate = true)
    val noteId: Long = 0,
    var name: String = "",
    var text: String = ""
)