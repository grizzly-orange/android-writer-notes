package com.grizzlyorange.writernotes.data.storages.roomdb.entities.note

import androidx.room.*
import com.grizzlyorange.writernotes.data.storages.roomdb.entities.notewithtags.NoteWithTags
import com.grizzlyorange.writernotes.data.storages.roomdb.entities.notewithtags.NotesAndTagsCrossRef
import kotlinx.coroutines.flow.Flow

@Dao
interface NoteDao {

    @Transaction
    @Query("Select * From NoteEntity")
    fun getAllNotesWithTagsFlow(): Flow<List<NoteWithTags>>

    @Transaction
    @Query("Select Distinct TagEntity.tagId, TagEntity.name, " +
            "NoteEntity.noteId, NoteEntity.name, NoteEntity.text, "+
            "NoteEntity.createDateInMillis, NoteEntity.updateDateInMillis from NotesAndTagsCrossRef \n" +
            "Inner Join TagEntity On TagEntity.tagId = NotesAndTagsCrossRef.tagId\n" +
            "Inner Join NoteEntity On NoteEntity.noteId = NotesAndTagsCrossRef.noteId\n" +
            "Where TagEntity.tagId in (:tagIds)\n" +
            "Order by NoteEntity.noteId")
    fun getNotesWithTagsByTagsFlow(tagIds: List<Long>): Flow<List<NoteWithTags>>

    @Insert
    suspend fun insert(note: NoteEntity): Long

    @Update
    suspend fun update(note: NoteEntity)

    @Delete
    suspend fun delete(notes: List<NoteEntity>)
}