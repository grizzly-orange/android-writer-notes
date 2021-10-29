package com.grizzlyorange.writernotes.data.storages.roomdb.mappers

import com.grizzlyorange.writernotes.data.storages.roomdb.entities.notewithtags.NoteWithTags
import com.grizzlyorange.writernotes.data.storages.roomdb.entities.tag.TagEntity
import com.grizzlyorange.writernotes.domain.models.Note

class NoteMapper {
    companion object {
        fun domainToRoom(note: Note): NoteWithTags {
            val noteRoom = com.grizzlyorange.writernotes.data.storages.roomdb.entities.note.NoteEntity(
                note.noteId,
                note.name,
                note.text,
                note.createDateInMillis,
                note.updateDateInMillis
            )
            val tagsRoom = mutableListOf<TagEntity>().apply {
                note.tags.forEach {
                    add(TagMapper.domainToRoom(it))
                }
            }
            return NoteWithTags(noteRoom, tagsRoom)
        }

        fun roomToDomain(noteWithTags: NoteWithTags): Note {
            val tags = mutableListOf<Note.Tag>().apply {
                noteWithTags.tags.forEach {
                    add(TagMapper.roomToDomain(it))
                }
            }
            val n = noteWithTags.note
            return Note(
                n.noteId,
                n.name,
                n.text,
                n.createDateInMillis,
                n.updateDateInMillis,
                tags
            )
        }
    }
}