package com.grizzlyorange.writernotes.data.storages.roomdb.entities.note

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class NoteEntity(
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
}