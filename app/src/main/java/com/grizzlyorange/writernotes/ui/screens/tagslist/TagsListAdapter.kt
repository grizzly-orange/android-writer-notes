package com.grizzlyorange.writernotes.ui.screens.tagslist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.grizzlyorange.writernotes.databinding.TagListItemBinding
import com.grizzlyorange.writernotes.ui.data.dto.TagDto
import com.grizzlyorange.writernotes.ui.utils.rvlistselection.RVListItemsSelection
import com.grizzlyorange.writernotes.ui.utils.rvlistselection.RVListItemsSelectionHandler

class TagsListAdapter (
    private val selectionProcessing: RVListItemsSelection<TagDto>,
    private val selectionHandler: RVListItemsSelectionHandler<TagDto>
) : ListAdapter<TagDto, TagsListAdapter.TagViewHolder>(TagViewHolder.TagComparator()) {

    init {
        selectionProcessing.setAdapter(this)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TagViewHolder {
        return TagViewHolder.create(parent, selectionHandler)
    }

    override fun onBindViewHolder(holder: TagViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item,
            selectionProcessing.isSelected(item))
    }

    class TagViewHolder(
        private val binding: TagListItemBinding,
        private val clickHandler: RVListItemsSelectionHandler<TagDto>
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(tagDto: TagDto, isSelected: Boolean) {
            binding.tag = tagDto
            binding.isSelected = isSelected

            binding.root.setOnClickListener {
                clickHandler.onRVListItemClick(tagDto, adapterPosition)
            }

            binding.root.setOnLongClickListener {
                clickHandler.onRVListItemLongClick(tagDto, adapterPosition)
            }
        }

        companion object {
            fun create(
                parent: ViewGroup,
                clickHandler: RVListItemsSelectionHandler<TagDto>
            ) : TagViewHolder {

                val binding = TagListItemBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false)

                return TagViewHolder(binding, clickHandler)
            }
        }

        class TagComparator() : DiffUtil.ItemCallback<TagDto>() {
            override fun areItemsTheSame(oldItem: TagDto, newItem: TagDto): Boolean {
                return oldItem === newItem
            }

            override fun areContentsTheSame(oldItem: TagDto, newItem: TagDto): Boolean {
                return oldItem == newItem
            }
        }
    }
}