package com.grizzlyorange.writernotes.ui.utils.rvlist.rvlistselection

import androidx.recyclerview.widget.RecyclerView
import javax.inject.Inject

class RVListItemsSelectionState<T> @Inject constructor() {
    private lateinit var listAdapter: RecyclerView.Adapter<*>
    private val selectedItems: MutableSet<T> = mutableSetOf()

    fun setAdapter(adapter: RecyclerView.Adapter<*>) {
        listAdapter = adapter
    }

    fun toggleSelection(item: T, position: Int) {
        if (selectedItems.contains(item)) {
            selectedItems.remove(item)
        } else {
            selectedItems.add(item)
        }
        listAdapter.notifyItemChanged(position)
    }

    fun isSelected(item: T): Boolean {
        return selectedItems.contains(item)
    }

    fun getSelection(): Set<T> {
        return selectedItems
    }

    fun getSelectionCount(): Int {
        return selectedItems.size
    }

    fun clear() {
        selectedItems.clear()
        listAdapter.notifyDataSetChanged()
    }
}