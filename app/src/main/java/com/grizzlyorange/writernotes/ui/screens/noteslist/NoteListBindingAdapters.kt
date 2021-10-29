package com.grizzlyorange.writernotes.ui.screens.noteslist

import android.content.Context
import android.view.View
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.grizzlyorange.writernotes.R
import com.grizzlyorange.writernotes.ui.data.dto.TagDto
import com.grizzlyorange.writernotes.ui.utils.formatters.DateTimeFormatter

@BindingAdapter("tags")
fun setupNoteTags(textView: TextView, tags: List<TagDto>) {
    textView.visibility = getTagsVisibility(tags)
    textView.text = getTagsText(textView.context, tags)
}

fun getTagsVisibility(tags: List<TagDto>): Int {
    return if (tags.isEmpty()) View.GONE else View.VISIBLE
}

fun getTagsText(contex: Context, tags: List<TagDto>): String {
    return if (tags.isEmpty()) {
        ""
    } else {
        val tagsText = tags.joinToString(", ") { it.name }
        contex.getString(R.string.txtTagsAtNoteListItem, tagsText)
    }
}

@BindingAdapter ("lastUpdateDateInMs")
fun setupCreateAndUpdateDate(
    textView: TextView,
    lastUpdateDateInMs: Long) {

    textView.text = textView.context.getString(
        R.string.txtLastUpdateDateAtNoteListItem,
        DateTimeFormatter.getDateTimeStr(lastUpdateDateInMs)
    )
}