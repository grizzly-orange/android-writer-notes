package com.grizzlyorange.writernotes.ui.screens.noteslist

import android.util.Log
import androidx.lifecycle.*
import com.grizzlyorange.writernotes.data.repositories.NotesRepository
import com.grizzlyorange.writernotes.domain.models.Tag
import com.grizzlyorange.writernotes.ui.data.dto.NoteDto
import com.grizzlyorange.writernotes.domain.notesfilter.NotesFilter
import com.grizzlyorange.writernotes.ui.utils.rvlist.rvlistselection.RVListItemsSelectionManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NotesListViewModel @Inject constructor(
    private val notesRep: NotesRepository,
    val listSelectionManager: RVListItemsSelectionManager<NoteDto>
) : ViewModel() {

    val listSelection get() = listSelectionManager.listSelection

    private val _filter: MutableLiveData<NotesFilter> = MutableLiveData<NotesFilter>(NotesFilter())
    val filter: NotesFilter get() = _filter.value ?: NotesFilter()

    val filteredNotes: LiveData<List<NoteDto>> = _filter.switchMap { f->
        notesRep.getNotesByFilterFlow(f).map { notes ->
            notes.map { note -> NoteDto(note) }
        }.asLiveData()
    }

    fun applyFilter(f: NotesFilter) {
        _filter.value = f.deepCopy()
    }

    fun deleteNotes(notes: Set<NoteDto>) {
        viewModelScope.launch(Dispatchers.IO) {
            notesRep.deleteNotes(notes.map { it.note })
        }
    }
}