package com.spbisya.navapp.fragments

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.spbisya.navapp.R
import com.spbisya.navapp.databinding.FragmentLayoutBinding
import com.spbisya.navapp.extensions.viewLifecycleAware

class ThirdFragment : Fragment(R.layout.fragment_layout) {
    private val binding by viewLifecycleAware { FragmentLayoutBinding.bind(requireView()) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.title.text = "Third fragment"
        binding.description.text = "Args: "
        binding.btn1.setOnClickListener {
            findNavController().popBackStack()
            Log.e("DRE", "backstack update, count is ${findNavController().backQueue.joinToString { it.destination.displayName }}")
        }
        binding.btn2.setOnClickListener { }
        binding.btn3.setOnClickListener { }
    }
}