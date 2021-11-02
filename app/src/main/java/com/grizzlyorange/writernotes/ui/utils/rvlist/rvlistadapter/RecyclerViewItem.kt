package com.grizzlyorange.writernotes.ui.utils.rvlist.rvlistadapter

interface RecyclerViewItem {
    fun isItemTheSame(other: RecyclerViewItem): Boolean
    fun isContentTheSame(other: RecyclerViewItem): Boolean
}