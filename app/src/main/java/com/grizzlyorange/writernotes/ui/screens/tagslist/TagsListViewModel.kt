package com.grizzlyorange.writernotes.ui.screens.tagslist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.grizzlyorange.writernotes.data.repositories.TagsRepository
import com.grizzlyorange.writernotes.ui.data.dto.TagDto
import com.grizzlyorange.writernotes.ui.utils.rvlist.rvlistselection.RVListItemsSelectionManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TagsListViewModel @Inject constructor(
    private val tagsRep: TagsRepository,
    val listSelectionManager: RVListItemsSelectionManager<TagDto>
) : ViewModel() {

    val listSelection get() = listSelectionManager.listSelection

    private val _tags: MutableLiveData<List<TagDto>> = MutableLiveData(listOf())
    val tags: LiveData<List<TagDto>> = _tags

    init {
        viewModelScope.launch(Dispatchers.IO) {
            tagsRep.getAllTagsFlow.collect { domainTags ->
                _tags.postValue(
                    domainTags.map { tag ->
                        TagDto(tag)
                    })
            }
        }
    }

    fun deleteTags(tags: Set<TagDto>) {
        viewModelScope.launch(Dispatchers.IO) {
            tagsRep.deleteTags(tags.map {
                it.tag
            }.toList())
        }
    }
}