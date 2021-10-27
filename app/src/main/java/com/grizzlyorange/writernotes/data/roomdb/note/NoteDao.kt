package com.grizzlyorange.writernotes.data.roomdb.note

import androidx.room.*
import com.grizzlyorange.writernotes.data.roomdb.notewithtags.NoteWithTags
import kotlinx.coroutines.flow.Flow

@Dao
interface NoteDao {

    @Transaction
    @Query("Select * From Note")
    fun getAllNotesWithTagsFlow(): Flow<List<NoteWithTags>>

    @Insert
    suspend fun insert(note: Note): Long

    @Update
    suspend fun update(note: Note)

    @Delete
    suspend fun delete(notes: List<Note>)
}