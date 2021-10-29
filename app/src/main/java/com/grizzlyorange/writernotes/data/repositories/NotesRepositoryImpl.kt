package com.grizzlyorange.writernotes.data.repositories

import com.grizzlyorange.writernotes.data.storages.roomdb.entities.note.NoteDao
import com.grizzlyorange.writernotes.data.storages.roomdb.entities.note.NoteEntity
import com.grizzlyorange.writernotes.data.storages.roomdb.entities.notewithtags.NotesAndTagsCrossRef
import com.grizzlyorange.writernotes.data.storages.roomdb.entities.notewithtags.NotesAndTagsCrossRefDao
import com.grizzlyorange.writernotes.data.storages.roomdb.entities.tag.TagEntity
import com.grizzlyorange.writernotes.data.storages.roomdb.mappers.NoteMapper
import com.grizzlyorange.writernotes.domain.models.Note
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class NotesRepositoryImpl @Inject constructor(
    private val noteDao: NoteDao,
    private val notesAndTagsCrossRefDao: NotesAndTagsCrossRefDao
) {
    val getAllNotesFlow get(): Flow<List<Note>> = noteDao.getAllNotesWithTagsFlow().map { notes ->
        notes.map {
            NoteMapper.roomToDomain(it)
        }
    }

    suspend fun createOrUpdateNote(noteDomain: Note) {
        val noteWithTags = NoteMapper.domainToRoom(noteDomain)
        val note = noteWithTags.note

        if (note.isCreated()) {
            updateNote(note, noteWithTags.tags)
        } else {
            createNote(note, noteWithTags.tags)
        }
    }

    private suspend fun createNote(note: NoteEntity, tags: List<TagEntity>) {
        val noteId = noteDao.insert(note)

        notesAndTagsCrossRefDao.insert(
            tags.map {
                NotesAndTagsCrossRef(noteId, it.tagId)
            }
        )
    }

    private suspend fun updateNote(note: NoteEntity, tags: List<TagEntity>) {
        notesAndTagsCrossRefDao.delete(
            notesAndTagsCrossRefDao.getByNoteId(note.noteId)
        )

        notesAndTagsCrossRefDao.insert(
            tags.map {
                NotesAndTagsCrossRef(note.noteId, it.tagId)
            }
        )

        noteDao.update(note)
    }

    suspend fun deleteNotes(notes: List<Note>) {
        val notesWithTags = notes.map {
            NoteMapper.domainToRoom(it)
        }
        noteDao.delete(notesWithTags.map { it.note })
    }
}