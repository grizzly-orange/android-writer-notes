package com.grizzlyorange.writernotes.ui.screens.tagslist

import android.content.DialogInterface
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.grizzlyorange.writernotes.R
import com.grizzlyorange.writernotes.databinding.FragmentTagsListBinding
import com.grizzlyorange.writernotes.domain.models.Note
import com.grizzlyorange.writernotes.ui.data.dto.TagDto
import com.grizzlyorange.writernotes.ui.utils.rvlistselection.RVListItemsSelectionHandler
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TagsListFragment :
    Fragment(),
    RVListItemsSelectionHandler<TagDto>,
    ActionMode.Callback {

    private var _binding: FragmentTagsListBinding? = null
    private val binding get() = _binding!!

    private val tagsVM: TagsListViewModel by viewModels()
    private var actionMode: ActionMode? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentTagsListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initRVTagsList()
        initSelectionAndActionModeProcessing()
        initUIActionsListeners()
        restoreActionMode()
    }

    private fun initRVTagsList() {
        val adapter = TagsListAdapter(tagsVM.listSelection, this)
        binding.rvTagsList.adapter = adapter
        binding.rvTagsList.layoutManager = LinearLayoutManager(requireContext())

        tagsVM.tags.observe(viewLifecycleOwner, {
            adapter.submitList(it)
        })
    }

    private fun initSelectionAndActionModeProcessing() {
        tagsVM.listSelectionManager.actionModeTurnedOn.observe(
            viewLifecycleOwner, { actionModeTurnedOn ->
                if (actionModeTurnedOn == true) {
                    binding.fabAddTag.hide()
                } else {
                    binding.fabAddTag.show()
                }
            })

        tagsVM.listSelectionManager.selectedItemsCount.observe(
            viewLifecycleOwner, { selectedItemsCount ->
                updateActionModeTitle(selectedItemsCount)
            }
        )
    }

    private fun restoreActionMode() {
        if (tagsVM.listSelectionManager.isActionMode == true &&
            actionMode == null) {
            turnOnActionMode()
        }
    }

    private fun initUIActionsListeners() {
        binding.fabAddTag.setOnClickListener {
            moveToDetails(null)
        }
    }

    private fun moveToDetails(tag: Note.Tag?) {

    }

    override fun onRVListItemClick(item: TagDto, position: Int) {
        if (tagsVM.listSelectionManager.isActionMode == true) {
            tagsVM.listSelectionManager.toggleItemSelection(item, position)
        } else {
            moveToDetails(item.tag)
        }
    }

    override fun onRVListItemLongClick(item: TagDto, position: Int): Boolean {
        var actionModeWasTurnedOn = false
        if (tagsVM.listSelectionManager.isActionMode != true) {
            actionModeWasTurnedOn = turnOnActionMode()
        }
        tagsVM.listSelectionManager.toggleItemSelection(item, position)

        return actionModeWasTurnedOn
    }

    private fun turnOnActionMode(): Boolean {
        return when (actionMode) {
            null -> {
                actionMode = activity?.startActionMode(this)
                true
            }
            else -> false
        }
    }

    private fun turnOffActionMode() {
        actionMode?.finish()
    }

    private fun updateActionModeTitle(selectedItemsCount: Int) {
        actionMode?.title = selectedItemsCount.toString()
    }

    override fun onCreateActionMode(mode: ActionMode?, menu: Menu?): Boolean {
        mode?.menuInflater?.inflate(R.menu.notes_list_action_mode_menu, menu)
        tagsVM.listSelectionManager.isActionMode = true
        return true
    }

    override fun onPrepareActionMode(mode: ActionMode?, menu: Menu?): Boolean {
        return false
    }

    override fun onActionItemClicked(mode: ActionMode?, menuItem: MenuItem?): Boolean {
        return when(menuItem?.itemId) {
            R.id.btDeleteTags -> {
                deleteSelectedItems()
                true
            }
            else -> false
        }
    }

    private fun deleteSelectedItems() {
        MaterialAlertDialogBuilder(requireContext())
            .setMessage(R.string.deleteNotesDialogMessage)
            .setNegativeButton(
                R.string.deleteNotesDialogNegativeButtonLabel, null)
            .setPositiveButton(
                R.string.deleteNotesDialogPositiveButtonLabel,
                DialogInterface.OnClickListener { dialog, which ->
                    tagsVM.deleteTags(tagsVM.listSelectionManager.getSelectedItems())
                    turnOffActionMode()
                })
            .show()
    }

    override fun onDestroyActionMode(p0: ActionMode?) {
        actionMode = null
        tagsVM.listSelectionManager.isActionMode = false
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
        actionMode = null
    }
}