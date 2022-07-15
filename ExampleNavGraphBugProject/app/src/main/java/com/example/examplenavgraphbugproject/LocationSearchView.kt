package com.example.examplenavgraphbugproject

import LocationListAdapter
import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.examplenavgraphbugproject.databinding.SearchViewBinding

class LocationSearchView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0
) : ConstraintLayout(context, attrs, defStyle), LocationSearchListListener {

    private val searchAdapter = LocationListAdapter(this)
    var listener: Listener? = null

    val binding: SearchViewBinding

    init {
        val inflater =
            getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        inflater.inflate(R.layout.search_view, this, true)
        binding = SearchViewBinding.inflate(inflater, this, true)

        binding.searchResults.apply {
            adapter = searchAdapter
            layoutManager = LinearLayoutManager(context)
        }

        binding.locationSearchEditText.requestFocus()
        binding.locationSearchEditText.addTextChangedListener(object : TextWatcher {
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                listener?.onQueryChanged(s.toString())
            }

            override fun afterTextChanged(s: Editable?) {}
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
        })

        binding.cancelTextView.setOnClickListener {
            listener?.onCancelSelected()
        }
    }

    fun setResults(results: List<LocationItem>) {
        searchAdapter.submitList(results)
    }

    override fun onItemClicked(checkableItem: LocationItem.Location, position: Int) {
        listener?.onItemSelected(position, checkableItem)
    }

    interface Listener {
        fun onQueryChanged(newQuery: String)

        fun onItemSelected(position: Int, item: LocationItem.Location)

        fun onCancelSelected()
    }

}