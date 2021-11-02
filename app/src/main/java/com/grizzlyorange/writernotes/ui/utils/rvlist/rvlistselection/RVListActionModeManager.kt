package com.grizzlyorange.writernotes.ui.utils.rvlist.rvlistselection

import android.view.ActionMode
import android.view.Menu
import android.view.MenuItem
import com.grizzlyorange.writernotes.ui.utils.rvlist.rvlistadapter.RecyclerViewItem

class RVListActionModeManager<T: RecyclerViewItem>(
    private val client: RVListActionModeClient<T>
) : ActionMode.Callback,
    RVListItemsSelectionHandler<T> {

    private val listSelectionManager get() = client.listSelectionManager
    private var actionMode: ActionMode? = null

    override fun onRVListItemClick(item: T, position: Int) {
        if (listSelectionManager.isActionMode == true) {
            listSelectionManager.toggleItemSelection(item, position)
        } else {
            client.onClickListItemOutsideOfActiveMode(item)
        }
    }

    override fun onRVListItemLongClick(item: T, position: Int): Boolean {
        var actionModeWasTurnedOn = false
        if (listSelectionManager.isActionMode != true) {
            actionModeWasTurnedOn = turnOnActionMode()
        }
        listSelectionManager.toggleItemSelection(item, position)

        return actionModeWasTurnedOn
    }

    private fun turnOnActionMode(): Boolean {
        return when (actionMode) {
            null -> {
                actionMode = client.activityForActionMode?.startActionMode(this)
                true
            }
            else -> false
        }
    }

    fun turnOffActionMode() {
        actionMode?.finish()
    }

    fun updateActionModeTitle(selectedItemsCount: Int) {
        actionMode?.title = selectedItemsCount.toString()
    }

    fun restoreActionMode() {
        if (listSelectionManager.isActionMode == true &&
            actionMode == null) {
            turnOnActionMode()
        }
    }

    fun destroy() {
        actionMode = null
    }

    override fun onCreateActionMode(mode: ActionMode?, menu: Menu?): Boolean {
        mode?.menuInflater?.inflate(client.menuId, menu)
        listSelectionManager.isActionMode = true
        return true
    }

    override fun onPrepareActionMode(mode: ActionMode?, menu: Menu?): Boolean {
        return false
    }

    override fun onActionItemClicked(mode: ActionMode?, menuItem: MenuItem?): Boolean {
        if (menuItem == null)
            return false
        return client.onActionModeMenuItemClicked(menuItem.itemId)
    }

    override fun onDestroyActionMode(p0: ActionMode?) {
        actionMode = null
        listSelectionManager.isActionMode = false
    }
}