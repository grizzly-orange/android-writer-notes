package com.grizzlyorange.writernotes.data.roomdb.entities.notewithtags

import androidx.room.Entity
import androidx.room.ForeignKey
import com.grizzlyorange.writernotes.data.roomdb.entities.note.NoteEntity
import com.grizzlyorange.writernotes.data.roomdb.entities.tag.TagEntity

@Entity(
    primaryKeys = ["noteId", "tagId"],
    foreignKeys = [ForeignKey(
        entity = NoteEntity::class,
        parentColumns = arrayOf("noteId"),
        childColumns = arrayOf("noteId"),
        onDelete = ForeignKey.CASCADE
    ), ForeignKey(
        entity = TagEntity::class,
        parentColumns = arrayOf("tagId"),
        childColumns = arrayOf("tagId"),
        onDelete = ForeignKey.CASCADE
    )]
)
class NotesAndTagsCrossRef(
    val noteId: Long,
    val tagId: Long
)