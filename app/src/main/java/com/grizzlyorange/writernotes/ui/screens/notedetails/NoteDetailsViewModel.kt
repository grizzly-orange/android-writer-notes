package com.grizzlyorange.writernotes.ui.screens.notedetails

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.grizzlyorange.writernotes.data.note.Note
import com.grizzlyorange.writernotes.data.note.NotesRepositoryImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

@HiltViewModel
class NoteDetailsViewModel @Inject constructor(
    private val notesRep: NotesRepositoryImpl
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
    }

    private fun updateCurrentNoteValue(note: Note) {
        _note.value = note
    }

    fun saveCurrentNote(): Boolean {
        val note = _note.value ?: return false

        // TODO: check errors
        note.refreshDateTime(Calendar.getInstance().timeInMillis)
        viewModelScope.launch(Dispatchers.IO) {
            notesRep.createOrUpdateNote(note)
        }
        return true
    }
}