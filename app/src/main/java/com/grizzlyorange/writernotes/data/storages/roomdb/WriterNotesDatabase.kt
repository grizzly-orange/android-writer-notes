package com.grizzlyorange.writernotes.data.storages.roomdb

import androidx.room.Database
import androidx.room.RoomDatabase
import com.grizzlyorange.writernotes.data.storages.roomdb.entities.note.NoteEntity
import com.grizzlyorange.writernotes.data.storages.roomdb.entities.note.NoteDao
import com.grizzlyorange.writernotes.data.storages.roomdb.entities.notewithtags.NotesAndTagsCrossRef
import com.grizzlyorange.writernotes.data.storages.roomdb.entities.notewithtags.NotesAndTagsCrossRefDao
import com.grizzlyorange.writernotes.data.storages.roomdb.entities.tag.TagEntity
import com.grizzlyorange.writernotes.data.storages.roomdb.entities.tag.TagDao

@Database(
    entities = [NoteEntity::class,
        TagEntity::class,
        NotesAndTagsCrossRef::class],
    version = 1,
    exportSchema = false)
abstract class WriterNotesDatabase : RoomDatabase() {
    abstract fun noteDao(): NoteDao
    abstract fun tagDao(): TagDao
    abstract fun notesAndTagsCrossRefDao(): NotesAndTagsCrossRefDao
}