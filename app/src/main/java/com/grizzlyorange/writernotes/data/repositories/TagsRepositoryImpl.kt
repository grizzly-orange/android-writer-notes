package com.grizzlyorange.writernotes.data.repositories

import com.grizzlyorange.writernotes.data.storages.roomdb.entities.tag.TagDao
import com.grizzlyorange.writernotes.data.storages.roomdb.mappers.TagMapper
import com.grizzlyorange.writernotes.domain.models.Note
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class TagsRepositoryImpl @Inject constructor(
    private val tagDao: TagDao
) {

    val getAllTagsFlow get(): Flow<List<Note.Tag>> = tagDao.getAllTagsFlow().map { tags ->
        tags.map { tag ->
            TagMapper.roomToDomain(tag)
        }
    }

    suspend fun deleteTags(tags: List<Note.Tag>) {
        tagDao.delete(tags.map {
            TagMapper.domainToRoom(it)
        })
    }
}