package com.grizzlyorange.writernotes.ui.utils.rvlist.rvlistadapter

interface RecyclerViewItem {
    var isSelected: Boolean
    fun isItemTheSame(other: RecyclerViewItem): Boolean
    fun isContentTheSame(other: RecyclerViewItem): Boolean
}