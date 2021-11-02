package com.grizzlyorange.writernotes.ui.utils.rvlist.rvlistselection

import androidx.annotation.MenuRes
import androidx.fragment.app.FragmentActivity
import com.grizzlyorange.writernotes.ui.utils.rvlist.rvlistadapter.RecyclerViewItem

interface RVListActionModeClient <T: RecyclerViewItem> {
    val activityForActionMode: FragmentActivity?
    val listSelectionManager: RVListItemsSelectionManager<T>
    @get: MenuRes
    val menuId: Int
    fun onActionModeMenuItemClicked(menuItemId: Int): Boolean
    fun onClickListItemOutsideOfActiveMode(item: T)
}