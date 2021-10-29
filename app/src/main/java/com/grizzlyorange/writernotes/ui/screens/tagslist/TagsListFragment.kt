package com.grizzlyorange.writernotes.ui.screens.tagslist

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.LinearLayoutManager
import com.grizzlyorange.writernotes.R
import com.grizzlyorange.writernotes.databinding.FragmentTagsListBinding
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
    }

    private fun initRVTagsList() {
        val adapter = TagsListAdapter(tagsVM.listSelection, this)
        binding.rvTagsList.adapter = adapter
        binding.rvTagsList.layoutManager = LinearLayoutManager(requireContext())

        tagsVM.tags.observe(viewLifecycleOwner, {
            adapter.submitList(it)
        })
    }

    override fun onRVListItemClick(item: TagDto, position: Int) {
        TODO("Not yet implemented")
    }

    override fun onRVListItemLongClick(item: TagDto, position: Int): Boolean {
        TODO("Not yet implemented")
    }

    override fun onCreateActionMode(p0: ActionMode?, p1: Menu?): Boolean {
        TODO("Not yet implemented")
    }

    override fun onPrepareActionMode(p0: ActionMode?, p1: Menu?): Boolean {
        TODO("Not yet implemented")
    }

    override fun onActionItemClicked(p0: ActionMode?, p1: MenuItem?): Boolean {
        TODO("Not yet implemented")
    }

    override fun onDestroyActionMode(p0: ActionMode?) {
        TODO("Not yet implemented")
    }
}