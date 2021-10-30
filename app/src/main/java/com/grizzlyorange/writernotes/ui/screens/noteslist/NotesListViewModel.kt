package com.grizzlyorange.writernotes.ui.screens.noteslist

import androidx.lifecycle.*
import com.grizzlyorange.writernotes.data.repositories.NotesRepositoryImpl
import com.grizzlyorange.writernotes.ui.data.dto.NoteDto
import com.grizzlyorange.writernotes.ui.utils.rvlist.rvlistselection.RVListItemsSelectionManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NotesListViewModel @Inject constructor(
    private val notesRep: NotesRepositoryImpl,
    val listSelectionManager: RVListItemsSelectionManager<NoteDto>
) : ViewModel() {

    val listSelection get() = listSelectionManager.listSelection

    private val _notes: MutableLiveData<List<NoteDto>> = MutableLiveData(listOf())
    val notes: LiveData<List<NoteDto>> get() = _notes

    init {
        viewModelScope.launch(Dispatchers.IO) {
            notesRep.getAllNotesFlow.collect { notes ->
                _notes.postValue(
                    notes.map { note ->
                        NoteDto(note)
                    })
            }
        }
    }

    fun deleteNotes(notes: Set<NoteDto>) {
        viewModelScope.launch(Dispatchers.IO) {
            notesRep.deleteNotes(notes.map { it.note })
        }
    }
}