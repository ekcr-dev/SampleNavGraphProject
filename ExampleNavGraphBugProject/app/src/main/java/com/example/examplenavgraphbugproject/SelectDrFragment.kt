package com.example.examplenavgraphbugproject

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.examplenavgraphbugproject.databinding.FragmentSelectDrBinding
import javax.inject.Inject

class SelectDrFragment : Fragment(), Injectable, DrAdapter.Listener {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private var _binding: FragmentSelectDrBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: SelectDrViewModel

    private lateinit var parentViewModel: IntroViewModel

    private val drAdapter = DrAdapter(this)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSelectDrBinding
            .inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModel = ViewModelProvider(
            requireParentFragment().childFragmentManager.fragments[0],
            viewModelFactory
        )[SelectDrViewModel::class.java]

        parentViewModel = ViewModelProvider(
            requireParentFragment().childFragmentManager.fragments[0],
            viewModelFactory
        )[IntroViewModel::class.java]

        super.onViewCreated(view, savedInstanceState)

        initUi()
        setLiveData()

        arguments?.getString(ARG_ID)?.let {
            viewModel.getDrs()
        }
    }


    override fun onDestroyView() {
        binding.drRecyclerView.adapter = null
        super.onDestroyView()
        _binding = null
    }

    private fun initUi() {
        binding.drRecyclerView.apply {
            adapter = drAdapter
            layoutManager = LinearLayoutManager(context)
        }
    }

    private fun setLiveData() {
        parentViewModel.actionLiveData.observe(viewLifecycleOwner) {
            when (it) {
                is IntroViewModel.Action.OnSkipClicked -> {
                    viewModel.selectedDr = null
                }
            }
        }

        viewModel.commandLiveData.observe(viewLifecycleOwner) {
            when (it) {
                is SelectDrViewModel.Command.OnDrSelected -> showSelectionDialog()
            }

        }

        viewModel.locationDrsLiveData.observe(viewLifecycleOwner) {
            //root container?
            drAdapter.setLocationDrs(it)
        }

    }

    private fun showSelectionDialog() {
        val dialog =
            OneButtonDialog.newInstance(
                getString(R.string.selected_title),
                getString(R.string.selected_subtitle),
                getString(R.string.ok)
            ).also {
                it.listener =
                    object : OneButtonDialog.Listener {
                        override fun onCtaClicked() {
                            it.dismiss()
                            navigateToNext()
                        }
                    }
            }
        dialog.show(childFragmentManager, "")
    }

    override fun onDrSelected(doctor: Doctor) {
        viewModel.selectedDr = doctor
    }

    override fun onNoDrSelected() {
        viewModel.selectedDr = null
    }

    private fun navigateToNext() {
        findNavController().navigate(R.id.action_selectDrFragment_to_finalFragment)
    }

    companion object {
        private const val ARG_ID = "id"

        fun createBundle(locationId: String): Bundle {
            return bundleOf(ARG_ID to locationId)
        }
    }

    //provideViewModel??
}

