package com.grizzlyorange.writernotes.data.repositories

import com.grizzlyorange.writernotes.data.storages.roomdb.entities.tag.TagDao
import com.grizzlyorange.writernotes.data.storages.roomdb.mappers.TagMapper
import com.grizzlyorange.writernotes.domain.models.Tag
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class TagsRepository @Inject constructor(
    private val tagDao: TagDao
) {

    val getAllTagsFlow get(): Flow<List<Tag>> = tagDao.getAllTagsFlow().map { tags ->
        tags.map { tag ->
            TagMapper.roomToDomain(tag)
        }
    }

    suspend fun deleteTags(tags: List<Tag>) {
        tagDao.delete(tags.map {
            TagMapper.domainToRoom(it)
        })
    }

    suspend fun createOrUpdateTag(tag: Tag) {
        if (tag.isCreated()) {
            tagDao.update(TagMapper.domainToRoom(tag))
        } else {
            tagDao.insert(TagMapper.domainToRoom(tag))
        }
    }
}