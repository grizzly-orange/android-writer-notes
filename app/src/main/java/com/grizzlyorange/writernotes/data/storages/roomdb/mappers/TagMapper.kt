package com.grizzlyorange.writernotes.data.storages.roomdb.mappers

import com.grizzlyorange.writernotes.data.storages.roomdb.entities.tag.TagEntity
import com.grizzlyorange.writernotes.domain.models.Tag

class TagMapper {
    companion object {
        fun domainToRoom(tag: Tag): TagEntity {
            return TagEntity(tag.tagId, tag.name)
        }

        fun roomToDomain(tag: TagEntity): Tag {
            return Tag(tag.tagId, tag.name)
        }
    }
}