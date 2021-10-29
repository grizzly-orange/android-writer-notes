package com.grizzlyorange.writernotes.ui.data.dto

import com.grizzlyorange.writernotes.domain.models.Note

data class TagDto(
    val tag: Note.Tag
) {
    val name get() = tag.name
    val id get() = tag.id
}