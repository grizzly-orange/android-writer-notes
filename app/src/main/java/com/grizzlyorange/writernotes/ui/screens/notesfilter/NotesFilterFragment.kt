package com.grizzlyorange.writernotes.ui.screens.notesfilter

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.grizzlyorange.writernotes.R
import com.grizzlyorange.writernotes.databinding.FragmentNotesFilterBinding
import com.grizzlyorange.writernotes.ui.screens.noteslist.NotesListViewModel
import com.grizzlyorange.writernotes.ui.screens.tagdetails.TagDetailsViewModel
import com.grizzlyorange.writernotes.ui.screens.tagslist.TagsListViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NotesFilterFragment : Fragment() {

    private var _binding: FragmentNotesFilterBinding? = null
    private val binding get() = _binding!!

    private val filterVM: NotesFilterViewModel by activityViewModels()
    private val tagDetailsVM: TagDetailsViewModel by activityViewModels()
    private val notesListVM: NotesListViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentNotesFilterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.tagsHeader = getString(R.string.txtTagsHeaderAtNotesFilter)
        binding.tagDetailsVM = tagDetailsVM
        binding.tagsSourceAndHandler = filterVM
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.notes_filter_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId) {
            R.id.btApplyNotesFilter -> {
                notesListVM.applyFilter(filterVM.filter)
                moveToList()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun moveToList() {
        findNavController()
            .navigate(R.id.action_notesFilterFragment_to_notesListFragment)
    }
}