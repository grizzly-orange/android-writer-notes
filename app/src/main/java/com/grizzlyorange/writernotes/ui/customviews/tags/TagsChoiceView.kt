package com.grizzlyorange.writernotes.ui.customviews.tags

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import com.grizzlyorange.writernotes.databinding.TagChoiceViewBinding
import com.grizzlyorange.writernotes.ui.customviews.utils.ViewsUtils
import com.grizzlyorange.writernotes.ui.screens.tagdetails.TagDetailsDialogFragment
import com.grizzlyorange.writernotes.ui.screens.tagdetails.TagDetailsViewModel

class TagsChoiceView(
    context: Context,
    attributeSet: AttributeSet
) : FrameLayout(context, attributeSet) {

    private var tagDetailsVM: TagDetailsViewModel? = null

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

    fun setTagDetailsViewModel(tagVM: TagDetailsViewModel) {
        tagDetailsVM = tagVM
    }

    fun showTagDialog() {

        val activity = ViewsUtils.getActivity(this, context)
        if (activity != null) {

            tagDetailsVM?.setCurrentTag(null)
            TagDetailsDialogFragment()
                .show(
                    activity.supportFragmentManager,
                    "tagDetailsDialogFragment")
        }
    }
}