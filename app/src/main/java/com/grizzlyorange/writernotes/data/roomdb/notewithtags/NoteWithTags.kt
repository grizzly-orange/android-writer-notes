package com.grizzlyorange.writernotes.data.roomdb.notewithtags

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import com.grizzlyorange.writernotes.data.roomdb.note.Note
import com.grizzlyorange.writernotes.data.roomdb.tag.Tag

data class NoteWithTags(
    @Embedded
    val note: Note,
    @Relation(
        parentColumn = "noteId",
        entityColumn = "tagId",
        associateBy = Junction(NotesAndTagsCrossRef::class)
    )
    val tags: List<Tag>
)