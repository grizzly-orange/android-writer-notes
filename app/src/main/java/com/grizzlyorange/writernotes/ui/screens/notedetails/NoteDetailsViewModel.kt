package com.grizzlyorange.writernotes.ui.screens.notedetails

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.grizzlyorange.writernotes.data.repositories.NotesRepositoryImpl
import com.grizzlyorange.writernotes.data.repositories.TagsRepositoryImpl
import com.grizzlyorange.writernotes.domain.models.Note
import com.grizzlyorange.writernotes.domain.models.Tag
import com.grizzlyorange.writernotes.ui.customviews.tags.TagsSourceAndHandler
import com.grizzlyorange.writernotes.ui.data.dto.TagDto
import com.grizzlyorange.writernotes.ui.data.errors.ErrorsStorage
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

@HiltViewModel
class NoteDetailsViewModel @Inject constructor(
    private val notesRep: NotesRepositoryImpl,
    private val tagsRep: TagsRepositoryImpl,
    val noteErrorsStorage: ErrorsStorage
) : ViewModel(), TagsSourceAndHandler {

    private val _note: MutableLiveData<Note> = MutableLiveData()

    var noteName: String
        get() = _note.value?.name ?: ""
        set(value) {
            val note = _note.value ?: return
            if (note.name != value) {
                note.name = value
                updateCurrentNoteValue(note, false)
            }
        }

    var noteText: String
        get() = _note.value?.text ?: ""
        set(value) {
            val note = _note.value ?: return
            if (note.text != value) {
                note.text = value
                updateCurrentNoteValue(note, false)
            }
        }

    private val _allTags: MutableLiveData<List<TagDto>> = MutableLiveData(listOf())
    override val allTags: LiveData<List<TagDto>> get() = _allTags
    private val _selectedTags: MutableLiveData<List<TagDto>> = MutableLiveData(listOf())
    override val selectedTags: LiveData<List<TagDto>> get() = _selectedTags

    init {
        viewModelScope.launch (Dispatchers.IO) {
            tagsRep.getAllTagsFlow.collect { tags ->
                _allTags.postValue(
                    tags.map { tag ->
                        TagDto(tag)
                    })
            }
        }
    }

    override fun onTagSelected(tag: Tag, isChecked: Boolean) {
        Log.d("***", "onTagSelect ${tag} ${isChecked}")
        Log.d("***", "onTagSelect before ${_note.value?.tags} ")

        val n = _note.value ?: return
        val tagsIds = n.tags.map { it.id }
        Log.d("***", "onTagSelect contains ${tagsIds.contains(tag.id)} ")
        if (tagsIds.contains(tag.id) && !isChecked) {
            n.tags.remove(tag)
            updateCurrentNoteValue(n)
        } else if (!tagsIds.contains(tag.id) && isChecked) {
            n.tags.add(tag)
            updateCurrentNoteValue(n)
        }
        Log.d("***", "onTagSelect after ${_note.value?.tags} ")
    }

    fun setCurrentNote(note: Note?) {
        Log.d("***", "setCurrentNote ${note}")
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

    private fun updateCurrentNoteValue(note: Note, updateTags: Boolean = true) {
        checkAndRemoveErrors(note)
        if (updateTags) {
            _selectedTags.value = note.tags.map { TagDto(it) }
        }
        _note.value = note
    }
}