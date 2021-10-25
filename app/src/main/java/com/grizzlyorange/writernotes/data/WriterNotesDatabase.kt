package com.grizzlyorange.writernotes.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.grizzlyorange.writernotes.data.note.Note
import com.grizzlyorange.writernotes.data.note.NoteDao

@Database(
    entities = [Note::class],
    version = 1,
    exportSchema = false)
abstract class WriterNotesDatabase : RoomDatabase() {
    abstract fun noteDao(): NoteDao
}