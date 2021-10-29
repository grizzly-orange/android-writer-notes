package com.grizzlyorange.writernotes.data.roomdb.entities.tag

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface TagDao {

    @Query("Select * From TagEntity")
    fun getAllTagsFlow(): Flow<List<TagEntity>>

    @Insert
    suspend fun insert(tag: TagEntity)

    @Update
    suspend fun update(tag: TagEntity)

    @Delete
    suspend fun delete(tag: TagEntity)
}