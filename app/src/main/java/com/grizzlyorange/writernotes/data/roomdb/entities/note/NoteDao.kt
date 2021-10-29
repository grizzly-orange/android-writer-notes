package com.grizzlyorange.writernotes.data.roomdb.entities.note

import androidx.room.*
import com.grizzlyorange.writernotes.data.roomdb.entities.notewithtags.NoteWithTags
import kotlinx.coroutines.flow.Flow

@Dao
interface NoteDao {

    @Transaction
    @Query("Select * From NoteEntity")
    fun getAllNotesWithTagsFlow(): Flow<List<NoteWithTags>>

    @Insert
    suspend fun insert(note: NoteEntity): Long

    @Update
    suspend fun update(note: NoteEntity)

    @Delete
    suspend fun delete(notes: List<NoteEntity>)
}