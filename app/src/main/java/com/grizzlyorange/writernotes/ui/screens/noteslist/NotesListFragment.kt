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
import com.grizzlyorange.writernotes.databinding.FragmentNotesListBinding
import com.grizzlyorange.writernotes.domain.models.Note
import com.grizzlyorange.writernotes.ui.data.dto.NoteDto
import com.grizzlyorange.writernotes.ui.data.dto.TagDto
import com.grizzlyorange.writernotes.ui.screens.notedetails.NoteDetailsViewModel
import com.grizzlyorange.writernotes.ui.utils.rvlistselection.RVListActionModeClient
import com.grizzlyorange.writernotes.ui.utils.rvlistselection.RVListActionModeManager
import com.grizzlyorange.writernotes.ui.utils.rvlistselection.RVListItemsSelectionHandler
import com.grizzlyorange.writernotes.ui.utils.rvlistselection.RVListSelectionNotifier
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NotesListFragment :
    Fragment() {

    private var _binding: FragmentNotesListBinding? = null
    private val binding get() = _binding!!

    private val notesVM: NotesListViewModel by viewModels()
    private val noteDetailsVM: NoteDetailsViewModel by activityViewModels()

    private lateinit var actionModeManager: RVListActionModeManager<NoteDto>

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

        initActionModeManager()
        initRVNoteList()
        initSelectionAndActionModeProcessing()
        initUIActionsListeners()
        restoreActionMode()
    }

    private fun initActionModeManager() {
        val actionModeClient = object : RVListActionModeClient<NoteDto> {

            override val activityForActionMode get() = activity
            override val listSelectionManager: RVListSelectionNotifier<NoteDto> = notesVM.listSelectionManager
            override val menuId: Int get() = R.menu.notes_list_action_mode_menu

            override fun onActionModeMenuItemClicked(menuItemId: Int): Boolean {
                return onActionModeMenuItem(menuItemId)
            }

            override fun onClickOutsideActiveMode(item: NoteDto) {
                moveToDetails(item.note)
            }
        }

        actionModeManager = RVListActionModeManager<NoteDto>(actionModeClient)
    }


    private fun restoreActionMode() {
        actionModeManager.restoreActionMode()
    }

    private fun initRVNoteList() {
        val adapter = NotesListAdapter(notesVM.listSelection, actionModeManager)
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
                actionModeManager.
                    updateActionModeTitle(selectedItemsCount)
            }
        )
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
                R.id.btManageTags -> {
                    moveToTags()
                    true
                }
                else -> super.onOptionsItemSelected(item)
            }
    }

    private fun moveToFilter() {
        findNavController().navigate(
            R.id.action_notesListFragment_to_notesFilterFragment)
    }

    private fun moveToTags() {
        findNavController().navigate(
            R.id.action_notesListFragment_to_tagsListFragment)
    }

    private fun moveToDetails(note: Note?) {
        noteDetailsVM.setCurrentNote(note)
        findNavController().navigate(
            R.id.action_notesListFragment_to_noteDetailsFragment)
    }


    fun onActionModeMenuItem(menuItemId: Int): Boolean {
        return when(menuItemId) {
            R.id.btDeteteNotes -> {
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
                R.string.deleteDialogNegativeButtonLabel, null)
            .setPositiveButton(
                R.string.deleteDialogPositiveButtonLabel,
                DialogInterface.OnClickListener { dialog, which ->
                    notesVM.deleteNotes(notesVM.listSelectionManager.getSelectedItems())
                    actionModeManager.turnOffActionMode()
                })
            .show()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
        actionModeManager.destroy()
    }
}