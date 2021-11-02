package com.grizzlyorange.writernotes.ui.screens.tagslist

import RVListAdapter
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.grizzlyorange.writernotes.R
import com.grizzlyorange.writernotes.databinding.FragmentTagsListBinding
import com.grizzlyorange.writernotes.domain.models.Tag
import com.grizzlyorange.writernotes.ui.data.dto.TagDto
import com.grizzlyorange.writernotes.ui.screens.tagdetails.TagDetailsDialogFragment
import com.grizzlyorange.writernotes.ui.screens.tagdetails.TagDetailsViewModel
import com.grizzlyorange.writernotes.ui.utils.rvlist.rvlistselection.RVListActionModeClient
import com.grizzlyorange.writernotes.ui.utils.rvlist.rvlistselection.RVListActionModeManager
import com.grizzlyorange.writernotes.ui.utils.rvlist.rvlistselection.RVListItemsSelectionManager
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TagsListFragment :
    Fragment() {

    private var _binding: FragmentTagsListBinding? = null
    private val binding get() = _binding!!

    private val tagsVM: TagsListViewModel by viewModels()
    private val tagDetailsVM: TagDetailsViewModel by activityViewModels()

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
            override val listSelectionManager: RVListItemsSelectionManager<TagDto> = tagsVM.listSelectionManager
            override val menuId: Int get() = R.menu.tags_list_action_mode_menu

            override fun onActionModeMenuItemClicked(menuItemId: Int): Boolean {
                return onActionModeMenuItem(menuItemId)
            }

            override fun onClickListItemOutsideOfActiveMode(item: TagDto) {
                moveToDetails(item.tag)
            }
        }

        actionModeManager = RVListActionModeManager<TagDto>(actionModeClient)
    }

    private fun restoreActionMode() {
        actionModeManager.restoreActionMode()
    }

    private fun initRVTagsList() {
        val adapter = RVListAdapter<TagDto>(
            R.layout.tag_list_item,
            tagsVM.listSelection,
            actionModeManager)
        binding.rvTagsList.adapter = adapter
        binding.rvTagsList.layoutManager = LinearLayoutManager(requireContext())

        tagsVM.tags.observe(viewLifecycleOwner, { tags->
            adapter.submitList(tags)
            setupHeader(tags.isEmpty())
        })
    }

    private fun setupHeader(isTagsListEmpty: Boolean) {
        val header = binding.txtHeaderAtTagsList
        if (isTagsListEmpty) {
            header.visibility = View.VISIBLE
            header.text = getString(R.string.txtTagsListEmptyAtTagsList)
        } else {
            header.visibility = View.GONE
            header.text = ""
        }
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

    private fun moveToDetails(tag: Tag?) {
        tagDetailsVM.setCurrentTag(tag)
        TagDetailsDialogFragment()
            .show(childFragmentManager, "tagDetailsDialogFragment")
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
                R.string.deleteDialogPositiveButtonLabel
            ) { _, _ ->
                tagsVM.deleteTags(tagsVM.listSelectionManager.getSelectedItems())
                actionModeManager.turnOffActionMode()
            }
            .show()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
        actionModeManager.destroy()
        // TODO: check leaks
    }
}