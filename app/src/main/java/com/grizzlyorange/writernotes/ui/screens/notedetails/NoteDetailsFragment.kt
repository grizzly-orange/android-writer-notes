package com.grizzlyorange.writernotes.ui.screens.notedetails

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.grizzlyorange.writernotes.R
import com.grizzlyorange.writernotes.databinding.FragmentNoteDetailsBinding
import com.grizzlyorange.writernotes.ui.customviews.tags.TagsSourceAndHandler
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NoteDetailsFragment : Fragment() {

    private var _binding: FragmentNoteDetailsBinding? = null
    private val binding get() = _binding!!

    private val noteDetailsVM: NoteDetailsViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentNoteDetailsBinding.inflate(inflater, container, false)
        binding.noteDetailsVM = noteDetailsVM
        binding.lifecycleOwner = viewLifecycleOwner
        // data for tags view
        binding.tagsHeader = getString(R.string.txtTagsHeaderAtNoteDetails)
        binding.tagsSourceAndHandler = noteDetailsVM

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.note_details_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId) {
            R.id.btSaveNote -> {
                val isFinished = noteDetailsVM.saveCurrentNote()
                if (isFinished) {
                    moveToList()
                }
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun moveToList() {
        findNavController().navigate(
            R.id.action_noteDetailsFragment_to_notesListFragment
        )
    }
}