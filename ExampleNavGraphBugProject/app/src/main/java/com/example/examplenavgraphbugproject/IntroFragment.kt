package com.example.examplenavgraphbugproject

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.examplenavgraphbugproject.databinding.FragmentIntroBinding
import javax.inject.Inject

class IntroFragment : Fragment(), Injectable {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var parentViewModel: IntroViewModel

    private var _binding: FragmentIntroBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentIntroBinding
            .inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        parentViewModel = ViewModelProvider(
            requireParentFragment().childFragmentManager.fragments[0],
            viewModelFactory
        )[IntroViewModel::class.java]

        super.onViewCreated(view, savedInstanceState)
        setListeners()
    }

    private fun setListeners() {
        binding.cardView.setOnClickListener { navigateToSearch() }
    }

    private fun navigateToSearch() =
        findNavController().navigate(R.id.action_introFragment_to_locationFragment)

}