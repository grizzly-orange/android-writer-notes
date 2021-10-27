package com.grizzlyorange.writernotes.ui.data.dto

import com.grizzlyorange.writernotes.domain.models.Note
import java.text.SimpleDateFormat

data class NoteDto(val note: Note) {
    val name: String get() = note.name
    val text: String get() = note.text
    val createDate: String get() = getDateStr(note.createDateInMillis)
    val updateDate: String get() = getDateStr(note.updateDateInMillis)

    private fun getDateStr(timeInMs: Long): String {
        return SimpleDateFormat
            .getDateInstance()
            .format(timeInMs)
    }
}