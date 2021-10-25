package com.grizzlyorange.writernotes.data.note

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Note(
    @PrimaryKey(autoGenerate = true)
    val noteId: Long = 0,
    var name: String = "",
    var text: String = "",
    var createDateInMillis: Long = 0,
    var updateDateInMillis: Long = 0
) {
    fun isCreated(): Boolean {
        return noteId != 0L
    }
    fun refreshDateTime(timeInMillis: Long) {
        if (!isCreated()) {
            createDateInMillis = timeInMillis
        }
        updateDateInMillis = timeInMillis
    }
}