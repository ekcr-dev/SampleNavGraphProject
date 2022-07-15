import androidx.recyclerview.widget.RecyclerView
import com.example.examplenavgraphbugproject.LocationItem
import com.example.examplenavgraphbugproject.LocationSearchListListener
import com.example.examplenavgraphbugproject.databinding.LocationSearchItemBinding


class LocationItemViewHolder(
    private val binding: LocationSearchItemBinding,
    listener: LocationSearchListListener?
) : RecyclerView.ViewHolder(binding.root) {

    private var location: LocationItem.Location? = null

    init {
        itemView.setOnClickListener {
            location?.let { item ->
                listener?.onItemClicked(item, adapterPosition)
            }
        }
    }

    fun bind(item: LocationItem.Location) {
        this.location = item
        binding.title.text = item.name
    }
}