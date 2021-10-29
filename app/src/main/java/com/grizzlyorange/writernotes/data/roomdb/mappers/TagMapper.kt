package com.grizzlyorange.writernotes.data.roomdb.mappers

import com.grizzlyorange.writernotes.data.roomdb.entities.tag.TagEntity
import com.grizzlyorange.writernotes.domain.models.Note

class TagMapper {
    companion object {
        fun domainToRoom(tag: Note.Tag): TagEntity {
            return TagEntity(tag.id, tag.name)
        }

        fun roomToDomain(tag: TagEntity): Note.Tag {
            return Note.Tag(tag.tagId, tag.name)
        }
    }
}