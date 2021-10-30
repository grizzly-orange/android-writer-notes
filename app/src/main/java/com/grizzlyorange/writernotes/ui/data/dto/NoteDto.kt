package com.grizzlyorange.writernotes.ui.data.dto

import com.grizzlyorange.writernotes.domain.models.Note
import com.grizzlyorange.writernotes.ui.utils.rvlist.rvlistadapter.RecyclerViewItem
import java.text.SimpleDateFormat

data class NoteDto(val note: Note) : RecyclerViewItem {
    val name: String get() = note.name
    val text: String get() = note.text
    val createDate: Long get() = note.createDateInMillis
    val updateDate: Long get() = note.updateDateInMillis
    val tags: List<TagDto> get() = note.tags.map { TagDto(it) }

    val hasUpdateDate: Boolean get() = (note.updateDateInMillis != 0L)
    val lastUpdateDate: Long get() {
        return if (note.updateDateInMillis == 0L) {
            note.createDateInMillis
        } else
            note.updateDateInMillis
        }
    }