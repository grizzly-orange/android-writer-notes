package com.grizzlyorange.writernotes.ui.screens.noteslist

import androidx.lifecycle.*
import com.grizzlyorange.writernotes.data.note.NotesRepositoryImpl
import com.grizzlyorange.writernotes.ui.data.dto.NoteDTOUI
import com.grizzlyorange.writernotes.ui.utils.rvlistselection.RVListSelectionNotifier
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NotesListViewModel @Inject constructor(
    private val notesRep: NotesRepositoryImpl,
    val listSelectionManager: RVListSelectionNotifier<NoteDTOUI>
) : ViewModel() {
    private val _notes: MutableLiveData<List<NoteDTOUI>> = MutableLiveData(listOf())
    val notes: LiveData<List<NoteDTOUI>> get() = _notes

    init {
        viewModelScope.launch(Dispatchers.IO) {
            notesRep.getAllNotesFlow.collect { notes ->
                _notes.postValue(
                    notes.map { note ->
                        NoteDTOUI(note)
                    })
            }
        }
    }

    fun deleteNotes(notes: Set<NoteDTOUI>) {
        viewModelScope.launch(Dispatchers.IO) {
            notesRep.deleteNotes(notes.map { it.note })
        }
    }
}