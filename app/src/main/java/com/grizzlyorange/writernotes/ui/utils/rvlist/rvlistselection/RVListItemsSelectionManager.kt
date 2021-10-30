package com.grizzlyorange.writernotes.ui.utils.rvlist.rvlistselection

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import javax.inject.Inject

class RVListItemsSelectionManager<T> @Inject constructor(
    val listSelection: RVListItemsSelectionState<T>
) {

    private val _isActionMode: MutableLiveData<Boolean?> = MutableLiveData(null)
    var actionModeTurnedOn: LiveData<Boolean?> = _isActionMode

    var isActionMode: Boolean?
        get() = _isActionMode.value
        set(value) {
            val oldValue = _isActionMode.value
            if (value != oldValue) {
                _isActionMode.value = value
                if (value == false) {
                    listSelection.clear()
                } else {
                    updateSelectedItemsCount()
                }
            }
        }

    private val _selectedItemsCount: MutableLiveData<Int> = MutableLiveData(0)
    val selectedItemsCount: LiveData<Int> get() = _selectedItemsCount

    fun toggleItemSelection(item: T, position: Int) {
        listSelection.toggleSelection(item, position)
        updateSelectedItemsCount()
    }

    fun getSelectedItems(): Set<T> {
        return listSelection.getSelection()
    }

    private fun updateSelectedItemsCount() {
        _selectedItemsCount.value = listSelection.getSelectionCount()
    }
}