package com.grizzlyorange.writernotes.ui.noteslist

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.grizzlyorange.writernotes.R
import com.grizzlyorange.writernotes.databinding.FragmentNotesListBinding


class NotesListFragment : Fragment() {

    private var _binding: FragmentNotesListBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentNotesListBinding.inflate(inflater, container, false)

        binding.fabAddNote.setOnClickListener {
            moveToDetails()
        }

        return binding.root
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

    private fun moveToDetails() {
        findNavController().navigate(
            R.id.action_notesListFragment_to_noteDetailsFragment)
    }
}