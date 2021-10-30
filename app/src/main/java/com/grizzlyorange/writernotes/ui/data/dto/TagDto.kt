package com.grizzlyorange.writernotes.ui.data.dto

import com.grizzlyorange.writernotes.domain.models.Tag
import com.grizzlyorange.writernotes.ui.utils.rvlist.rvlistadapter.RecyclerViewItem

data class TagDto (
    val tag: Tag
) : RecyclerViewItem  {
    val name get() = tag.name
    val id get() = tag.id
}