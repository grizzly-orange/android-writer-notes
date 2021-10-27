package com.grizzlyorange.writernotes.data.roomdb.notewithtags

import androidx.room.Entity
import androidx.room.ForeignKey
import com.grizzlyorange.writernotes.data.roomdb.note.Note
import com.grizzlyorange.writernotes.data.roomdb.tag.Tag

@Entity(
    primaryKeys = ["noteId", "tagId"],
    foreignKeys = [ForeignKey(
        entity = Note::class,
        parentColumns = arrayOf("noteId"),
        childColumns = arrayOf("noteId"),
        onDelete = ForeignKey.CASCADE
    ), ForeignKey(
        entity = Tag::class,
        parentColumns = arrayOf("tagId"),
        childColumns = arrayOf("tagId"),
        onDelete = ForeignKey.CASCADE
    )]
)
class NotesAndTagsCrossRef(
    val noteId: Long,
    val tagId: Long
)