
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.databinding.library.baseAdapters.BR
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.grizzlyorange.writernotes.ui.utils.rvlist.rvlistadapter.RecyclerViewItem
import com.grizzlyorange.writernotes.ui.utils.rvlist.rvlistselection.RVListItemsSelectionState
import com.grizzlyorange.writernotes.ui.utils.rvlist.rvlistselection.RVListItemsSelectionHandler

class RVListAdapter <T: RecyclerViewItem>(
    @LayoutRes
    private val itemLayoutId: Int,
    private val selectionState: RVListItemsSelectionState<T>,
    private val selectionHandler: RVListItemsSelectionHandler<T>
) : ListAdapter<T, RVListAdapter.ItemViewHolder<T>>(ItemViewHolder.ItemComparator()) {

    init {
        selectionState.setAdapter(this)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RVListAdapter.ItemViewHolder<T> {
        val binding = DataBindingUtil.inflate<ViewDataBinding>(
            LayoutInflater.from(parent.context),
            itemLayoutId,
            parent,
            false)
        return ItemViewHolder<T>(binding, selectionHandler)
    }

    override fun onBindViewHolder(holder: RVListAdapter.ItemViewHolder<T>, position: Int) {
        val item = getItem(position)
        holder.bind(item,
            selectionState.isSelected(item))
    }

    class ItemViewHolder<T: RecyclerViewItem>(
        private val binding: ViewDataBinding,
        private val clickHandler: RVListItemsSelectionHandler<T>
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: T, isSelected: Boolean) {
            binding.apply {
                setVariable(BR.item, item)
                setVariable(BR.isSelected, isSelected)
            }

            binding.root.setOnClickListener {
                clickHandler.onRVListItemClick(item, adapterPosition)
            }

            binding.root.setOnLongClickListener {
                clickHandler.onRVListItemLongClick(item, adapterPosition)
            }
        }

        class ItemComparator<T: RecyclerViewItem>() : DiffUtil.ItemCallback<T>() {
            override fun areItemsTheSame(oldItem: T, newItem: T): Boolean {
                return oldItem === newItem
            }

            override fun areContentsTheSame(oldItem: T, newItem: T): Boolean {
                return oldItem == newItem
            }
        }
    }
}