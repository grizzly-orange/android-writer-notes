package com.grizzlyorange.writernotes.ui.screens.noteslist

import androidx.lifecycle.*
import com.grizzlyorange.writernotes.data.note.NotesRepositoryImpl
import com.grizzlyorange.writernotes.ui.dto.Note
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NotesListViewModel @Inject constructor(
    private val notesRep: NotesRepositoryImpl
) : ViewModel() {
    private val _notes: MutableLiveData<List<Note>> = MutableLiveData(listOf())
    val notes: LiveData<List<Note>> get() = _notes

    init {
        viewModelScope.launch(Dispatchers.IO) {
            notesRep.getAllNotesFlow.collect { notes ->
                _notes.postValue(
                    notes.map { note ->
                        Note(note)
                    }
                )
            }
        }
    }
}