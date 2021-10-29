package com.grizzlyorange.writernotes.ui.customviews.tags

import androidx.lifecycle.LiveData
import com.grizzlyorange.writernotes.domain.models.Note
import com.grizzlyorange.writernotes.ui.data.dto.TagDto

interface TagsSourceAndHandler {
    val allTags: LiveData<List<TagDto>>
    val selectedTags: LiveData<List<TagDto>>
    fun onTagSelected(tag: Note.Tag, isChecked: Boolean)
}