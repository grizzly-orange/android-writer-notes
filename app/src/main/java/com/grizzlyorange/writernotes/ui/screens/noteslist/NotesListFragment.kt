package com.grizzlyorange.writernotes.ui.screens.noteslist

import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.grizzlyorange.writernotes.R
import com.grizzlyorange.writernotes.data.note.Note
import com.grizzlyorange.writernotes.databinding.FragmentNotesListBinding
import com.grizzlyorange.writernotes.ui.data.dto.NoteDTOUI
import com.grizzlyorange.writernotes.ui.screens.notedetails.NoteDetailsViewModel
import com.grizzlyorange.writernotes.ui.utils.rvlistselection.RVListItemsSelectionHandler
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NotesListFragment :
    Fragment(),
    RVListItemsSelectionHandler<NoteDTOUI>,
    ActionMode.Callback {

    private var _binding: FragmentNotesListBinding? = null
    private val binding get() = _binding!!

    private val notesVM: NotesListViewModel by viewModels()
    private val noteDetailsVM: NoteDetailsViewModel by activityViewModels()

    private var actionMode: ActionMode? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentNotesListBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initRVNoteList()
        initSelectionAndActionModeProcessing()
        initUIActionsListeners()
        restoreActionMode()
    }

    private fun initRVNoteList() {
        val adapter = NotesListAdapter(notesVM.listSelectionManager.listSelection, this)
        binding.rvNotesList.adapter = adapter
        binding.rvNotesList.layoutManager = LinearLayoutManager(requireContext())

        notesVM.notes.observe(viewLifecycleOwner, {
            adapter.submitList(it)
        })
    }

    private fun initSelectionAndActionModeProcessing() {
        notesVM.listSelectionManager.actionModeTurnedOn.observe(
            viewLifecycleOwner, { actionModeTurnedOn ->
                if (actionModeTurnedOn == true) {
                    binding.fabAddNote.hide()
                } else {
                    binding.fabAddNote.show()
                }
            })

        notesVM.listSelectionManager.selectedItemsCount.observe(
            viewLifecycleOwner, { selectedItemsCount ->
                updateActionModeTitle(selectedItemsCount)
            }
        )
    }

    private fun restoreActionMode() {
        if (notesVM.listSelectionManager.isActionMode == true &&
            actionMode == null) {
            turnOnActionMode()
        }
    }

    private fun initUIActionsListeners() {
        binding.fabAddNote.setOnClickListener {
            moveToDetails(null)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.notes_list_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId) {
                 R.id.btFilterNotes -> {
                     moveToFilter()
                    true
                }
                else -> super.onOptionsItemSelected(item)
            }
    }

    private fun moveToFilter() {
        findNavController().navigate(
            R.id.action_notesListFragment_to_notesFilterFragment)
    }

    private fun moveToDetails(note: Note?) {
        noteDetailsVM.setCurrentNote(note)
        findNavController().navigate(
            R.id.action_notesListFragment_to_noteDetailsFragment)
    }

    override fun onRVListItemClick(item: NoteDTOUI, position: Int) {
        if (notesVM.listSelectionManager.isActionMode == true) {
            notesVM.listSelectionManager.toggleItemSelection(item, position)
        } else {
            moveToDetails(item.note)
        }
    }

    override fun onRVListItemLongClick(item: NoteDTOUI, position: Int): Boolean {
        var actionModeWasTurnedOn = false
        if (notesVM.listSelectionManager.isActionMode != true) {
            actionModeWasTurnedOn = turnOnActionMode()
        }
        notesVM.listSelectionManager.toggleItemSelection(item, position)

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
        notesVM.listSelectionManager.isActionMode = true
        return true
    }

    override fun onPrepareActionMode(mode: ActionMode?, menu: Menu?): Boolean {
        return false
    }

    override fun onActionItemClicked(mode: ActionMode?, menuItem: MenuItem?): Boolean {
        return when(menuItem?.itemId) {
            R.id.btDeteteNotes -> {
                deleteSelectedItems()
                true
            }
            else -> false
        }
    }

    private fun deleteSelectedItems() {
        MaterialAlertDialogBuilder(requireContext())
            .setMessage("Delete")
            .setNegativeButton(
                "Cancel", null)
            .setPositiveButton(
                "Delete",
                DialogInterface.OnClickListener { dialog, which ->
                    //TODO: delete
                    turnOffActionMode()
                })
            .show()
    }

    override fun onDestroyActionMode(mode: ActionMode?) {
        actionMode = null
        notesVM.listSelectionManager.isActionMode = false
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
        actionMode = null
    }
}