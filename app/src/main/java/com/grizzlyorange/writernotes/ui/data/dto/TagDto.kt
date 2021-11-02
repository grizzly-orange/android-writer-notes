package com.grizzlyorange.writernotes.ui.data.dto

import com.grizzlyorange.writernotes.domain.models.Tag
import com.grizzlyorange.writernotes.ui.utils.rvlist.rvlistadapter.RecyclerViewItem

data class TagDto (
    val tag: Tag
) : RecyclerViewItem  {
    override var isSelected: Boolean = false
    val name get() = tag.name
    val id get() = tag.tagId

    override fun isItemTheSame(other: RecyclerViewItem): Boolean {
        return if (other is TagDto) {
            id == other.id
        } else {
            false
        }
    }

    override fun isContentTheSame(other: RecyclerViewItem): Boolean {
        return if (other is TagDto) {
            name == other.name
            isSelected == other.isSelected
        } else {
            false
        }
    }
}