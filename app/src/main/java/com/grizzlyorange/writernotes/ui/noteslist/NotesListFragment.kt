package com.grizzlyorange.writernotes.ui.noteslist

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.grizzlyorange.writernotes.R
import com.grizzlyorange.writernotes.data.note.Note
import com.grizzlyorange.writernotes.databinding.FragmentNotesListBinding
import com.grizzlyorange.writernotes.ui.notedetails.NoteDetailsViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NotesListFragment : Fragment() {

    private var _binding: FragmentNotesListBinding? = null
    private val binding get() = _binding!!

    private val notesVM: NotesListViewModel by viewModels()
    private val noteDetailsVM: NoteDetailsViewModel by activityViewModels()

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

        val adapter = NotesListAdapter()
        binding.rvNotesList.adapter = adapter
        binding.rvNotesList.layoutManager = LinearLayoutManager(requireContext())

        notesVM.notes.observe(viewLifecycleOwner, {
            adapter.submitList(it)
        })

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
        noteDetailsVM.setCurrentNote(null)
        findNavController().navigate(
            R.id.action_notesListFragment_to_noteDetailsFragment)
    }
}