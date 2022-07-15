package com.example.examplenavgraphbugproject

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.examplenavgraphbugproject.databinding.FragmentLocationSearchBinding
import javax.inject.Inject


class SearchFragment : Fragment(), Injectable, LocationSearchView.Listener {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private var _binding: FragmentLocationSearchBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: LocationSearchViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLocationSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(
            this,
            viewModelFactory
        )[LocationSearchViewModel::class.java]

        setLiveData()
        binding.locationSearchView.listener = this
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setLiveData() {
        viewModel.locationsLiveData.observe(viewLifecycleOwner) { locations ->
            binding.locationSearchView.setResults(getSearchItemList(locations))
        }
    }

    private fun getSearchItemList(locations: List<LocationItem.Location>): List<LocationItem> {
        val searchItems = ArrayList<LocationItem>()

        locations.forEach {
            searchItems.add(it.mapToSearchItem())
        }
        return searchItems
    }

    private fun LocationItem.Location.mapToSearchItem(): LocationItem =
        LocationItem.Location(
            this.id, this.name
        )


    override fun onItemSelected(position: Int, item: LocationItem.Location) {
        setLocationAndFinish(item.id, item.name)
    }

    override fun onCancelSelected() {
        activity?.finish()
    }

    override fun onQueryChanged(newQuery: String) {
        viewModel.searchQuery = newQuery
    }

    private fun setLocationAndFinish(
        locationId: String,
        locationName: String
    ) {
        val returnIntent = Intent()
        returnIntent.putExtra(
            SearchActivity.PARCEL_ARG,
            SearchActivity.Parcel(locationId, locationName)
        )
        activity?.setResult(Activity.RESULT_OK, returnIntent)
        activity?.finish()
    }

}
