package com.grizzlyorange.writernotes.ui.screens.notedetails

import androidx.lifecycle.*
import com.grizzlyorange.writernotes.data.repositories.NotesRepository
import com.grizzlyorange.writernotes.data.repositories.TagsRepository
import com.grizzlyorange.writernotes.domain.models.Note
import com.grizzlyorange.writernotes.domain.models.Tag
import com.grizzlyorange.writernotes.ui.customviews.tags.TagsSourceAndHandler
import com.grizzlyorange.writernotes.ui.data.dto.TagDto
import com.grizzlyorange.writernotes.ui.data.errors.ErrorsStorage
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

@HiltViewModel
class NoteDetailsViewModel @Inject constructor(
    private val notesRep: NotesRepository,
    private val tagsRep: TagsRepository,
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

    override val allTags: LiveData<List<TagDto>> =
        tagsRep.getAllTagsFlow.map { tagsList ->
            tagsList.map { tag -> TagDto(tag) }
        }.asLiveData()
    private val _selectedTags: MutableLiveData<List<TagDto>> = MutableLiveData(listOf())
    override val selectedTags: LiveData<List<TagDto>> get() = _selectedTags

    override fun onTagSelected(tag: Tag, isChecked: Boolean) {

        val n = _note.value ?: return
        val tagsIds = n.tags.map { it.tagId }

        if (tagsIds.contains(tag.tagId) && !isChecked) {
            n.tags.remove(tag)
            updateCurrentNoteValue(n)
        } else if (!tagsIds.contains(tag.tagId) && isChecked) {
            n.tags.add(tag)
            updateCurrentNoteValue(n)
        }
    }

    fun setCurrentNote(note: Note?) {
        if (note == null) {
            updateCurrentNoteValue(Note())
        } else {
            updateCurrentNoteValue(note.deepCopy())
        }
        noteErrorsStorage.resetErrors()
    }

    fun saveCurrentNote(): Boolean {
        val note = _note.value ?: return false

        note.refreshDateTime(Calendar.getInstance().timeInMillis)

        noteErrorsStorage.addErrors(
            note.checkAndGetErrors())
        if (noteErrorsStorage.hasErrors())
            return false

        viewModelScope.launch(Dispatchers.IO) {
            notesRep.saveNote(note)
        }
        return true
    }

    private fun updateCurrentNoteValue(note: Note, updateTags: Boolean = true) {
        noteErrorsStorage.removeAllBesides(
            note.checkAndGetErrors())

        if (updateTags) {
            _selectedTags.value = note.tags.map { TagDto(it) }
        }
        _note.value = note
    }
}