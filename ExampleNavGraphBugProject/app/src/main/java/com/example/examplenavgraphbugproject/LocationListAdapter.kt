import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.examplenavgraphbugproject.LocationItem
import com.example.examplenavgraphbugproject.LocationSearchListListener
import com.example.examplenavgraphbugproject.databinding.LocationSearchItemBinding


private const val SEARCH_ITEM = 1

class LocationListAdapter(
    val listener: LocationSearchListListener? = null
) : ListAdapter<LocationItem, RecyclerView.ViewHolder>(LocationSearchDiffUtil()) {

    class LocationSearchDiffUtil : DiffUtil.ItemCallback<LocationItem>() {
        override fun areItemsTheSame(
            oldItem: LocationItem,
            newItem: LocationItem
        ): Boolean = oldItem.id == newItem.id

        override fun areContentsTheSame(
            oldItem: LocationItem,
            newItem: LocationItem
        ): Boolean = oldItem.toString() == newItem.toString()
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecyclerView.ViewHolder {

        return when (viewType) {
            SEARCH_ITEM -> {
                val binding = LocationSearchItemBinding.inflate(
                    LayoutInflater.from(parent.context), parent, false
                )
                LocationItemViewHolder(binding, listener)
            }
            else -> throw ClassCastException("Unknown viewType $viewType")
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (getItem(position)) {
            is LocationItem.Location -> SEARCH_ITEM
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is LocationItemViewHolder -> {
                holder.bind(getItem(position) as LocationItem.Location)
            }

        }
    }

}