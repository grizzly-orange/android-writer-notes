package com.grizzlyorange.writernotes.data.note

import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class NotesRepositoryImpl @Inject constructor(
    private val noteDao: NoteDao
) {
    val getAllNotesFlow get() = noteDao.getAllNotesFlow()

    suspend fun createOrUpdateNote(note: Note) {
        if (note.isCreated()) {
            noteDao.update(note)
        } else {
            noteDao.insert(note)
        }
    }

    suspend fun deleteNotes(notes: List<Note>) {
        noteDao.delete(notes)
    }
}