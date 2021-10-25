package com.grizzlyorange.writernotes.data.note

import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class NotesRepositoryImpl @Inject constructor(
    private val noteDao: NoteDao
) {
    val getAllNotesFlow get() = noteDao.getAllNotesFlow()
}