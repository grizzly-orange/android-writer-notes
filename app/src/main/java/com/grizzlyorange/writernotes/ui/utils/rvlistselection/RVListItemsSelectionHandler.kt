package com.grizzlyorange.writernotes.ui.utils.rvlistselection

interface RVListItemsSelectionHandler<T> {
    fun onRVListItemClick(item: T, position: Int)
    fun onRVListItemLongClick(item: T, position: Int): Boolean
}