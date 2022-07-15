package com.example.examplenavgraphbugproject

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import com.example.examplenavgraphbugproject.databinding.FragmentLocationBinding
import javax.inject.Inject

class LocationFragment : Fragment(), Injectable {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    //sessionManager?

    private var _binding: FragmentLocationBinding? = null
    private val binding get() = _binding!!

    private lateinit var parentViewModel: IntroViewModel

    private lateinit var viewModel: LocationViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentLocationBinding
            .inflate(inflater, container, false)
        return binding.root
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == RequestCodes.SEARCH) {

            data?.getParcelableExtra<SearchActivity.Parcel>(SearchActivity.PARCEL_ARG)
                ?.let {
                    viewModel.apply {
                        selectedLocationName = it.name
                        selectedLocationId = it.id
                    }
                    binding.searchEditText.setText(it.name)
                }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initViewModels()
        super.onViewCreated(view, savedInstanceState)

        setLiveData()

        binding.searchEditText.setText(viewModel.selectedLocationName)

        binding.searchEditText.setOnClickListener {
            startActivityForResult(
                Intent(requireContext(), SearchActivity::class.java),
                RequestCodes.SEARCH
            )
        }

    }

    private fun initViewModels() {
        parentViewModel = ViewModelProvider(
            requireParentFragment().childFragmentManager.fragments[0],
            viewModelFactory
        )[IntroViewModel::class.java]

        viewModel = ViewModelProvider(
            requireParentFragment().childFragmentManager.fragments[0],
            viewModelFactory
        )[LocationViewModel::class.java]

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setLiveData() {
        parentViewModel.actionLiveData.observe(viewLifecycleOwner) {
            when (it) {
                is IntroViewModel.Action.OnNextClicked -> {
                    val selectedLocationId = viewModel.selectedLocationId
                    if (selectedLocationId != null) {
                        navigateToSelectDr(selectedLocationId)
                    }
                }
                is IntroViewModel.Action.OnSkipClicked -> {
                    viewModel.navigateToNext()
                }
            }
        }

        viewModel.commandLiveData.observe(viewLifecycleOwner) {
            when (it) {
                is LocationViewModel.Command.NavigateToSelectDr -> {
                    navigateToSelectDr(it.selectedLocationId)
                }
                is LocationViewModel.Command.NavigateToNext -> {
                    navigateToNext()
                }
            }
        }
    }

    private fun navigateToSelectDr(locationId: String) {
        findNavController()
            .navigate(
                R.id.action_locationFragment_to_selectDrFragment,
                SelectDrFragment.createBundle(locationId)
            )
    }

    private fun navigateToNext() {
        val options = getPopStackNavOptions(R.id.locationFragment)
        findNavController().navigate(
            R.id.action_locationFragment_to_finalFragment,
            null,
            options
        )
    }

    private fun getPopStackNavOptions(destination: Int): NavOptions =
        NavOptions.Builder().setPopUpTo(destination, true)
            .setPopEnterAnim(R.anim.enter_from_left)
            .setPopExitAnim(R.anim.exit_to_right)
            .build()

}

