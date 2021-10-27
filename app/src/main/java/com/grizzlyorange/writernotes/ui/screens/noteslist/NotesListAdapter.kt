package com.grizzlyorange.writernotes.ui.screens.noteslist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.grizzlyorange.writernotes.databinding.NotesListItemBinding
import com.grizzlyorange.writernotes.ui.data.dto.NoteDto
import com.grizzlyorange.writernotes.ui.utils.rvlistselection.RVListItemsSelection
import com.grizzlyorange.writernotes.ui.utils.rvlistselection.RVListItemsSelectionHandler

class NotesListAdapter (
    private val selectionProcessing: RVListItemsSelection<NoteDto>,
    private val selectionHandler: RVListItemsSelectionHandler<NoteDto>
) : ListAdapter<NoteDto,NotesListAdapter.NoteViewHolder>(NoteViewHolder.NoteComparator()) {

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
        private val clickHandler: RVListItemsSelectionHandler<NoteDto>
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(note: NoteDto, isSelected: Boolean) {
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
            fun create(parent: ViewGroup, clickHandler: RVListItemsSelectionHandler<NoteDto>): NoteViewHolder {

                val binding = NotesListItemBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false)

                return NoteViewHolder(binding, clickHandler)
            }
        }

        class NoteComparator() : DiffUtil.ItemCallback<NoteDto>() {
            override fun areItemsTheSame(oldItem: NoteDto, newItem: NoteDto): Boolean {
                return oldItem === newItem
            }

            override fun areContentsTheSame(oldItem: NoteDto, newItem: NoteDto): Boolean {
                return oldItem == newItem
            }
        }
    }
}