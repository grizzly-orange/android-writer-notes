package com.grizzlyorange.writernotes.ui.utils.rvlist.rvlistselection

import com.grizzlyorange.writernotes.ui.utils.rvlist.rvlistadapter.RecyclerViewItem

interface RVListItemsSelectionHandler<T: RecyclerViewItem> {
    fun onRVListItemClick(item: T, position: Int)
    fun onRVListItemLongClick(item: T, position: Int): Boolean
}