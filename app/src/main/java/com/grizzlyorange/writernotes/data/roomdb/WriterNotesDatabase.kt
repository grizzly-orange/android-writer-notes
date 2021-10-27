package com.grizzlyorange.writernotes.data.roomdb

import androidx.room.Database
import androidx.room.RoomDatabase
import com.grizzlyorange.writernotes.data.roomdb.note.Note
import com.grizzlyorange.writernotes.data.roomdb.note.NoteDao
import com.grizzlyorange.writernotes.data.roomdb.notewithtags.NotesAndTagsCrossRef
import com.grizzlyorange.writernotes.data.roomdb.tag.Tag

@Database(
    entities = [Note::class,
        Tag::class,
        NotesAndTagsCrossRef::class],
    version = 1,
    exportSchema = false)
abstract class WriterNotesDatabase : RoomDatabase() {
    abstract fun noteDao(): NoteDao
}