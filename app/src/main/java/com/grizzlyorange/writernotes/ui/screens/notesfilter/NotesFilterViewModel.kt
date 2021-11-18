package com.grizzlyorange.writernotes.ui.screens.notesfilter

import androidx.lifecycle.*
import com.grizzlyorange.writernotes.data.repositories.TagsRepository
import com.grizzlyorange.writernotes.domain.models.Note
import com.grizzlyorange.writernotes.domain.models.Tag
import com.grizzlyorange.writernotes.ui.customviews.tags.TagsSourceAndHandler
import com.grizzlyorange.writernotes.ui.data.dto.TagDto
import com.grizzlyorange.writernotes.domain.notesfilter.NotesFilter
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NotesFilterViewModel @Inject constructor(
    tagsRep: TagsRepository

) : ViewModel(), TagsSourceAndHandler {

    private val _filter: MutableLiveData<NotesFilter> = MutableLiveData(NotesFilter())
    val filter: NotesFilter get() = _filter.value ?: NotesFilter()

    override val selectedTags: LiveData<List<TagDto>> = _filter.switchMap { notesFilter ->
        liveData {
            emit(notesFilter.tags.map { tag -> TagDto(tag) })
        }
    }

    override val allTags: LiveData<List<TagDto>> =
        tagsRep.getAllTagsFlow.map { tagsList ->
            tagsList.map { tag -> TagDto(tag) }
        }.asLiveData()

    fun setupFilter(value: NotesFilter) {
        updateFilterValue(value.deepCopy())
    }

    override fun onTagSelected(tag: Tag, isChecked: Boolean) {
        val f = filter.deepCopy()
        if (f.tags.contains(tag) && !isChecked) {
            f.tags.remove(tag)
            updateFilterValue(f)
        } else if (!f.tags.contains(tag) && isChecked) {
            f.tags.add(tag)
            updateFilterValue(f)
        }
    }

    private fun updateFilterValue(value: NotesFilter) {
        _filter.value = value
    }
}