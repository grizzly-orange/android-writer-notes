package com.grizzlyorange.writernotes.data.storages.roomdb.entities.notewithtags

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import com.grizzlyorange.writernotes.data.storages.roomdb.entities.note.NoteEntity
import com.grizzlyorange.writernotes.data.storages.roomdb.entities.tag.TagEntity

data class NoteWithTags(
    @Embedded
    val note: NoteEntity,
    @Relation(
        parentColumn = "noteId",
        entityColumn = "tagId",
        associateBy = Junction(NotesAndTagsCrossRef::class)
    )
    val tags: List<TagEntity>
)