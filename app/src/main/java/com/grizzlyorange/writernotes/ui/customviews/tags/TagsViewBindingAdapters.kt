package com.grizzlyorange.writernotes.ui.customviews.tags

import android.util.Log
import androidx.core.view.ViewCompat
import androidx.core.view.children
import androidx.databinding.BindingAdapter
import com.google.android.material.chip.ChipGroup
import com.grizzlyorange.writernotes.ui.customviews.valuedviews.ValuedChip
import com.grizzlyorange.writernotes.ui.data.dto.TagDto

@BindingAdapter(value = ["allTags", "selectedTags", "tagsSelectionHandler", "chipStyle"], requireAll = true)
fun setupTags(
    chipGroup: ChipGroup,
    allTags: List<TagDto>?,
    selectedTags: List<TagDto>?,
    tagsSelectionHandler: TagsSourceAndHandler?,
    chipStyle: Int) {

    if (allTags == null || tagsSelectionHandler == null) {
        return
    }

    val chips = chipGroup.children.filter { it is ValuedChip<*> }.toList()
    chips.forEach {
        chipGroup.removeView(it)
    }

    allTags.forEach { tag ->
        val chip = ValuedChip(tag, chipGroup.context, chipStyle)
        chip.id = ViewCompat.generateViewId()
        chip.text = tag.name
        chip.setOnClickListener { tagChip ->
            ((tagChip as ValuedChip<*>).value as TagDto).tag.let { tag ->
                tagsSelectionHandler
                    .onTagSelected(tag, tagChip.isChecked)
            }
        }
        chipGroup.addView(chip)
    }

    setupSelectedTags(chipGroup, selectedTags)
}

@BindingAdapter("selectedTags")
fun setupSelectedTags(chipGroup: ChipGroup, selectedTags: List<TagDto>?) {
    Log.d("*** Binding", "selectedTags ${selectedTags}")
    if (selectedTags == null) {
        return
    }

    val selectedTagsIds = selectedTags.map { it.id }
    val chips = chipGroup.children.filter { it is ValuedChip<*> }
    chips.forEach { chip ->
        val chipValue = (chip as ValuedChip<*>).value as TagDto
        //Log.d("*** ", "chip ${chipValue} ${selectedTagsIds.contains(chipValue.id)} ${chip.isChecked}")
        if (selectedTagsIds.contains(chipValue.id) && !chip.isChecked) {
            chip.isChecked = true
        } else if (!selectedTagsIds.contains(chipValue.id) && chip.isChecked) {
            chip.isChecked = false
        }
    }
}

