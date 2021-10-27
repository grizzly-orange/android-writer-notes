package com.grizzlyorange.writernotes.data.repositories

import com.grizzlyorange.writernotes.data.roomdb.note.NoteDao
import com.grizzlyorange.writernotes.data.roomdb.note.NoteMapper
import com.grizzlyorange.writernotes.data.roomdb.notewithtags.NoteWithTags
import com.grizzlyorange.writernotes.domain.models.Note
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class NotesRepositoryImpl @Inject constructor(
    private val noteDao: NoteDao
) {
    val getAllNotesFlow get(): Flow<List<Note>> = noteDao.getAllNotesWithTagsFlow().map { notes ->
        notes.map {
            NoteMapper.roomToDomain(it)
        }
    }

    suspend fun createOrUpdateNote(note: Note) {
        val noteWithTags = NoteMapper.domainToRoom(note)
        if (noteWithTags.note.isCreated()) {
            noteDao.update(noteWithTags.note)
        } else {
            noteDao.insert(noteWithTags.note)
        }

        //TODO: delete old cross ref and insert new cross ref
    }

    suspend fun deleteNotes(notes: List<Note>) {
        val notesWithTags = notes.map {
            NoteMapper.domainToRoom(it)
        }
        noteDao.delete(notesWithTags.map { it.note })
        // TODO: check cascade crossref deletion
    }
}