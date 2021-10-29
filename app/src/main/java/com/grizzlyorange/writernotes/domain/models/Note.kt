package com.grizzlyorange.writernotes.domain.models

import com.grizzlyorange.writernotes.domain.DomainErrors

data class Note (
    val noteId: Long = 0,
    var name: String = "",
    var text: String = "",
    var createDateInMillis: Long = 0,
    var updateDateInMillis: Long = 0,
    val tags: MutableList<Tag> = mutableListOf<Tag>()
) {
    fun refreshDateTime(timeInMillis: Long) {
        if (!isCreated()) {
            createDateInMillis = timeInMillis
        }
        updateDateInMillis = timeInMillis
    }

    private fun isCreated(): Boolean {
        return noteId != 0L
    }

    fun checkAndGetErrors(): Set<DomainErrors> {
        val errors = mutableSetOf<DomainErrors>()
        if (name.isEmpty() && text.isEmpty()) {
            errors.add(DomainErrors.EMPTY_NOTE_NAME_OR_TEXT)
        }
        return errors
    }

    data class Tag(
        val id: Long,
        val name: String
    )
}