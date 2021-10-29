package com.grizzlyorange.writernotes.ui.utils.rvlistselection

import android.app.Activity
import androidx.fragment.app.FragmentActivity
import com.grizzlyorange.writernotes.ui.data.dto.TagDto

interface RVListActionModeClient <T> {
    val activityForActionMode: FragmentActivity?
    val listSelectionManager: RVListSelectionNotifier<T>
    val menuId: Int
    fun onActionModeMenuItemClicked(menuItemId: Int): Boolean
    fun onClickOutsideActiveMode(item: T)
}