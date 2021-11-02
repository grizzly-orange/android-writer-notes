package com.grizzlyorange.writernotes.domain.models

import com.grizzlyorange.writernotes.domain.errors.DomainErrors

data class Tag(
    val tagId: Long = 0,
    var name: String = ""
) {

    fun deepCopy(): Tag {
        return Tag(tagId, name)
    }

    fun isCreated(): Boolean {
        return (tagId != 0L)
    }

    fun checkAndGetErrors(): Set<DomainErrors> {
        val errors = mutableSetOf<DomainErrors>()
        if (name.isEmpty()) {
            errors.add(DomainErrors.EMPTY_TAG_NAME)
        }
        return errors
    }
}