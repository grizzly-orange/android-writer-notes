package com.grizzlyorange.writernotes.domain.notesfilter

import com.grizzlyorange.writernotes.domain.models.Tag

class NotesFilter(
    val tags: MutableList<Tag> = mutableListOf()
) {

    fun deepCopy(): NotesFilter {
        return NotesFilter(tags.toMutableList())
    }

    fun isEmptyTags(): Boolean {
        return tags.isEmpty()
    }

    fun getTagIds(): List<Long> {
        return tags.map { it.tagId }
    }
}