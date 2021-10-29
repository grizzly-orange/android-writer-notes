package com.grizzlyorange.writernotes.ui.screens.tagslist

import android.app.Activity
import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.grizzlyorange.writernotes.R
import com.grizzlyorange.writernotes.databinding.FragmentTagsListBinding
import com.grizzlyorange.writernotes.domain.models.Note
import com.grizzlyorange.writernotes.ui.data.dto.TagDto
import com.grizzlyorange.writernotes.ui.utils.rvlistselection.RVListActionModeClient
import com.grizzlyorange.writernotes.ui.utils.rvlistselection.RVListActionModeManager
import com.grizzlyorange.writernotes.ui.utils.rvlistselection.RVListSelectionNotifier
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TagsListFragment :
    Fragment() {

    private var _binding: FragmentTagsListBinding? = null
    private val binding get() = _binding!!

    private val tagsVM: TagsListViewModel by viewModels()

    private lateinit var actionModeManager: RVListActionModeManager<TagDto>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentTagsListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initActionModeManager()
        restoreActionMode()
        initRVTagsList()
        initSelectionAndActionModeProcessing()
        initUIActionsListeners()
    }

    private fun initActionModeManager() {
        val actionModeClient = object : RVListActionModeClient<TagDto> {

            override val activityForActionMode get() = activity
            override val listSelectionManager: RVListSelectionNotifier<TagDto> = tagsVM.listSelectionManager
            override val menuId: Int get() = R.menu.tags_list_action_mode_menu

            override fun onActionModeMenuItemClicked(menuItemId: Int): Boolean {
                return onActionModeMenuItem(menuItemId)
            }

            override fun onClickOutsideActiveMode(item: TagDto) {
                moveToDetails(item.tag)
            }
        }

        actionModeManager = RVListActionModeManager<TagDto>(actionModeClient)
    }

    private fun restoreActionMode() {
        actionModeManager.restoreActionMode()
    }

    private fun initRVTagsList() {
        val adapter = TagsListAdapter(tagsVM.listSelection, actionModeManager)
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
                actionModeManager
                    .updateActionModeTitle(selectedItemsCount)
            }
        )
    }

    private fun initUIActionsListeners() {
        binding.fabAddTag.setOnClickListener {
            moveToDetails(null)
        }
    }

    private fun moveToDetails(tag: Note.Tag?) {

    }

    fun onActionModeMenuItem(menuItemId: Int): Boolean {
        return when(menuItemId) {
            R.id.btDeleteTags -> {
                deleteSelectedItems()
                true
            }
            else -> false
        }
    }

    private fun deleteSelectedItems() {
        MaterialAlertDialogBuilder(requireContext())
            .setMessage(R.string.deleteTagsDialogMessage)
            .setNegativeButton(
                R.string.deleteDialogNegativeButtonLabel, null)
            .setPositiveButton(
                R.string.deleteDialogPositiveButtonLabel,
                DialogInterface.OnClickListener { dialog, which ->
                    tagsVM.deleteTags(tagsVM.listSelectionManager.getSelectedItems())
                    actionModeManager.turnOffActionMode()
                })
            .show()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
        actionModeManager.destroy()
        // TODO: check leaks
    }
}