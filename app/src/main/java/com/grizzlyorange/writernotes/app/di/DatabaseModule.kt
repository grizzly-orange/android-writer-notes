package com.grizzlyorange.writernotes.app.di

import android.content.Context
import androidx.room.Room
import com.grizzlyorange.writernotes.data.roomdb.WriterNotesDatabase
import com.grizzlyorange.writernotes.data.roomdb.note.NoteDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class DatabaseModule {

    @Provides
    fun providesNoteDao(writeNotesDB: WriterNotesDatabase): NoteDao {
        return writeNotesDB.noteDao()
    }

    @Provides
    @Singleton
    fun providesWriterNotesDB(@ApplicationContext appContext: Context): WriterNotesDatabase {
        return Room.databaseBuilder(
            appContext,
            WriterNotesDatabase::class.java,
            "WriterNotesDB"
        )
            .build()
    }
}