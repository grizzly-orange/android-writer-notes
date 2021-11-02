package com.grizzlyorange.writernotes.ui.screens.tagdetails

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.grizzlyorange.writernotes.data.repositories.TagsRepository
import com.grizzlyorange.writernotes.domain.models.Tag
import com.grizzlyorange.writernotes.ui.data.errors.ErrorsStorage
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TagDetailsViewModel @Inject constructor(
    private val tagsRep: TagsRepository,
    val tagsErrorsStorage: ErrorsStorage
) : ViewModel() {

    private val _tag: MutableLiveData<Tag> = MutableLiveData()

    var tagName: String
        get() = _tag.value?.name ?: ""
        set(value) {
            val t = _tag.value ?: return
            if (value != t.name) {
                t.name = value
                updateCurrentTag(t)
            }
        }

    fun setCurrentTag(tag: Tag?) {
        if (tag == null) {
            updateCurrentTag(Tag())
        } else {
            updateCurrentTag(tag.deepCopy())
        }

        tagsErrorsStorage.resetErrors()
    }

    fun saveCurrentTag(): Boolean {

        val tag = _tag.value ?: return false

        tagsErrorsStorage.addErrors(
            tag.checkAndGetErrors()
        )
        if (tagsErrorsStorage.hasErrors()) {
            return false
        }

        viewModelScope.launch(Dispatchers.IO) {
            tagsRep.createOrUpdateTag(tag)
        }

        return true
    }

    private fun updateCurrentTag(tag: Tag) {
        tagsErrorsStorage.removeAllBesides(
            tag.checkAndGetErrors())
        _tag.value = tag
    }
}