package com.grizzlyorange.writernotes.ui.data.dto

import android.util.Log
import com.grizzlyorange.writernotes.domain.models.Note
import com.grizzlyorange.writernotes.ui.utils.rvlist.rvlistadapter.RecyclerViewItem
import java.text.SimpleDateFormat

data class NoteDto(val note: Note) : RecyclerViewItem {
    override var isSelected: Boolean = false
    val id: Long get() = note.noteId
    val name: String get() = note.name
    val text: String get() = note.text
    val createDate: Long get() = note.createDateInMillis
    val updateDate: Long get() = note.updateDateInMillis
    val tags: List<TagDto> get() = note.tags.map { TagDto(it) }

    val lastUpdateDate: Long get() {
        return if (note.updateDateInMillis == 0L) {
            note.createDateInMillis
        } else
            note.updateDateInMillis
        }

    override fun isItemTheSame(other: RecyclerViewItem): Boolean {
        return if (other is NoteDto) {
            id == other.id
        } else {
            false
        }
    }

    override fun isContentTheSame(other: RecyclerViewItem): Boolean {
        return if (other is NoteDto) {
            name == other.name
            && text == other.text
            && updateDate == other.updateDate
            && tags == other.tags
            && isSelected == other.isSelected
        } else {
            false
        }
    }
}