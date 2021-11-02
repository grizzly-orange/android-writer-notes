package com.grizzlyorange.writernotes.ui.utils.rvlist.rvlistselection

import androidx.recyclerview.widget.RecyclerView
import com.grizzlyorange.writernotes.ui.utils.rvlist.rvlistadapter.RecyclerViewItem
import javax.inject.Inject

class RVListItemsSelectionState<T: RecyclerViewItem> @Inject constructor() {
    private lateinit var listAdapter: RecyclerView.Adapter<*>
    private val selectedItems: MutableSet<T> = mutableSetOf()

    fun setAdapter(adapter: RecyclerView.Adapter<*>) {
        listAdapter = adapter
    }

    fun toggleSelection(item: T, position: Int) {
        if (selectedItems.contains(item)) {
            selectedItems.remove(item)
            item.isSelected = false
        } else {
            selectedItems.add(item)
            item.isSelected = true
        }
        listAdapter.notifyItemChanged(position)
    }

    fun getSelection(): Set<T> {
        return selectedItems
    }

    fun getSelectionCount(): Int {
        return selectedItems.size
    }

    fun clear() {
        selectedItems.forEach {
            it.isSelected = false
        }
        selectedItems.clear()
        listAdapter.notifyDataSetChanged()
    }
}