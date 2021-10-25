package com.grizzlyorange.writernotes.ui.screens.noteslist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.grizzlyorange.writernotes.databinding.NotesListItemBinding
import com.grizzlyorange.writernotes.ui.data.dto.Note

class NotesListAdapter :
    ListAdapter<Note,NotesListAdapter.NoteViewHolder>(NoteViewHolder.NoteComparator()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        return NoteViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class NoteViewHolder(
        private val binding: NotesListItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(note: Note) {
            binding.note = note
        }

        companion object {
            fun create(parent: ViewGroup): NoteViewHolder {

                val binding = NotesListItemBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false)

                return NoteViewHolder(binding)
            }
        }

        class NoteComparator() : DiffUtil.ItemCallback<Note>() {
            override fun areItemsTheSame(oldItem: Note, newItem: Note): Boolean {
                return oldItem === newItem
            }

            override fun areContentsTheSame(oldItem: Note, newItem: Note): Boolean {
                return oldItem == newItem
            }
        }
    }
}