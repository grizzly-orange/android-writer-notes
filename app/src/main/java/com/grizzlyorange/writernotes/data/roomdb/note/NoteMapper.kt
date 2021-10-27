package com.grizzlyorange.writernotes.data.roomdb.note

import com.grizzlyorange.writernotes.data.roomdb.notewithtags.NoteWithTags
import com.grizzlyorange.writernotes.data.roomdb.tag.Tag
import com.grizzlyorange.writernotes.domain.models.Note

class NoteMapper {
    companion object {
        fun domainToRoom(note: Note): NoteWithTags {
            val noteRoom = com.grizzlyorange.writernotes.data.roomdb.note.Note(
                note.noteId,
                note.name,
                note.text,
                note.createDateInMillis,
                note.updateDateInMillis
            )
            val tagsRoom = mutableListOf<Tag>().apply {
                note.tags.forEach {
                    add(Tag(it.id, it.name))
                }
            }
            return NoteWithTags(noteRoom, tagsRoom)
        }

        fun roomToDomain(noteWithTags: NoteWithTags): Note {
            val tags = mutableListOf<Note.Tag>().apply {
                noteWithTags.tags.forEach {
                    add(Note.Tag(it.tagId, it.name))
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