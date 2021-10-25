package com.grizzlyorange.writernotes.data.note

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface NoteDao {

    @Query("Select * from Note")
    fun getAllNotesFlow(): Flow<List<Note>>

    @Insert
    suspend fun insert(note: Note): Long

    @Update
    suspend fun update(note: Note)
}