package com.grizzlyorange.writernotes.ui.customviews.tags

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.widget.FrameLayout
import com.grizzlyorange.writernotes.databinding.TagChoiceViewBinding
import com.grizzlyorange.writernotes.ui.customviews.utils.ViewsUtils

class TagsChoiceView(
    context: Context,
    attributeSet: AttributeSet
) : FrameLayout(context, attributeSet) {

    //private lateinit var tagDetailViewModel

    private val binding = TagChoiceViewBinding.inflate(LayoutInflater.from(context), this, true)

    init {
        binding.lifecycleOwner = ViewsUtils.getLifeCycleOwner(this)
        binding.actionsHandler = this
    }

    fun setTagsSourceAndHandler(tagsSourceAndHandler: TagsSourceAndHandler) {
        binding.tagsSourceAndHandler = tagsSourceAndHandler
    }

    fun setTagsViewHeader(header: String) {
        binding.tagsViewHeader = header
    }

    fun setChipStyle(style: Int) {
        binding.tagChipStyle = style
    }

    fun showTagDialog() {
        Log.d("TagsChoiceView", "showTagDialog()")
    }
}