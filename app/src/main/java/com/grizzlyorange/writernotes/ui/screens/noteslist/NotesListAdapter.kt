package com.grizzlyorange.writernotes.ui.screens.noteslist

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.grizzlyorange.writernotes.databinding.NotesListItemBinding
import com.grizzlyorange.writernotes.ui.data.dto.NoteDTOUI
import com.grizzlyorange.writernotes.ui.utils.rvlistselection.RVListItemsSelection
import com.grizzlyorange.writernotes.ui.utils.rvlistselection.RVListItemsSelectionHandler

class NotesListAdapter (
    private val selectionProcessing: RVListItemsSelection<NoteDTOUI>,
    private val selectionHandler: RVListItemsSelectionHandler<NoteDTOUI>
) : ListAdapter<NoteDTOUI,NotesListAdapter.NoteViewHolder>(NoteViewHolder.NoteComparator()) {

    init {
        selectionProcessing.setAdapter(this)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        return NoteViewHolder.create(parent, selectionHandler)
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item,
            selectionProcessing.isSelected(item))
    }

    class NoteViewHolder(
        private val binding: NotesListItemBinding,
        private val clickHandler: RVListItemsSelectionHandler<NoteDTOUI>
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(note: NoteDTOUI, isSelected: Boolean) {
            binding.note = note
            binding.isSelected = isSelected

            binding.root.setOnClickListener {
                clickHandler.onRVListItemClick(note, adapterPosition)
            }

            binding.root.setOnLongClickListener {
                clickHandler.onRVListItemLongClick(note, adapterPosition)
            }
        }

        companion object {
            fun create(parent: ViewGroup, clickHandler: RVListItemsSelectionHandler<NoteDTOUI>): NoteViewHolder {

                val binding = NotesListItemBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false)

                return NoteViewHolder(binding, clickHandler)
            }
        }

        class NoteComparator() : DiffUtil.ItemCallback<NoteDTOUI>() {
            override fun areItemsTheSame(oldItem: NoteDTOUI, newItem: NoteDTOUI): Boolean {
                return oldItem === newItem
            }

            override fun areContentsTheSame(oldItem: NoteDTOUI, newItem: NoteDTOUI): Boolean {
                return oldItem == newItem
            }
        }
    }
}