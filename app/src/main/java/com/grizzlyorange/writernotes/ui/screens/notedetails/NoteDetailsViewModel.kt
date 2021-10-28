package com.grizzlyorange.writernotes.ui.screens.notedetails

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.grizzlyorange.writernotes.data.repositories.NotesRepositoryImpl
import com.grizzlyorange.writernotes.domain.DomainErrors
import com.grizzlyorange.writernotes.domain.models.Note
import com.grizzlyorange.writernotes.ui.data.errors.ErrorsMap
import com.grizzlyorange.writernotes.ui.data.errors.ErrorsStorage
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

@HiltViewModel
class NoteDetailsViewModel @Inject constructor(
    private val notesRep: NotesRepositoryImpl,
    val noteErrorsStorage: ErrorsStorage
) : ViewModel() {

    private val _note: MutableLiveData<Note> = MutableLiveData()

    var noteName: String
        get() = _note.value?.name ?: ""
        set(value) {
            val note = _note.value ?: return
            if (note.name != value) {
                note.name = value
                updateCurrentNoteValue(note)
            }
        }

    var noteText: String
        get() = _note.value?.text ?: ""
        set(value) {
            val note = _note.value ?: return
            if (note.text != value) {
                note.text = value
                updateCurrentNoteValue(note)
            }
        }

    fun setCurrentNote(note: Note?) {
        if (note == null) {
            updateCurrentNoteValue(Note())
        } else {
            updateCurrentNoteValue(note)
        }
        noteErrorsStorage.resetErrors()
    }

    fun saveCurrentNote(): Boolean {
        val note = _note.value ?: return false

        note.refreshDateTime(Calendar.getInstance().timeInMillis)

        checkAndSetupErrors(note)
        if (noteErrorsStorage.hasErrors())
            return false

        viewModelScope.launch(Dispatchers.IO) {
            notesRep.createOrUpdateNote(note)
        }
        return true
    }

    private fun checkAndSetupErrors(note: Note) {
        noteErrorsStorage.addErrors(
            note.checkAndGetErrors()
        )
    }

    private fun checkAndRemoveErrors(note: Note) {
        noteErrorsStorage.removeAllBesides(
            note.checkAndGetErrors()
        )
    }

    private fun updateCurrentNoteValue(note: Note) {
        checkAndRemoveErrors(note)
        _note.value = note
    }
}