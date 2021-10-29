package com.grizzlyorange.writernotes.data.storages.roomdb.entities.notewithtags

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface NotesAndTagsCrossRefDao {

    @Query("Select * From NotesAndTagsCrossRef Where noteId = :noteId")
    suspend fun getByNoteId(noteId: Long): List<NotesAndTagsCrossRef>

    @Insert
    suspend fun insert(n: NotesAndTagsCrossRef)

    @Insert
    suspend fun insert(n: List<NotesAndTagsCrossRef>)

    @Delete
    suspend fun delete(n: NotesAndTagsCrossRef)

    @Delete
    suspend fun delete(n: List<NotesAndTagsCrossRef>)
}