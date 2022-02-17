package com.spbisya.navapp.fragments

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.os.bundleOf
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.spbisya.navapp.R
import com.spbisya.navapp.databinding.FragmentLayoutBinding
import com.spbisya.navapp.extensions.extra
import com.spbisya.navapp.extensions.launch
import com.spbisya.navapp.extensions.viewLifecycleAware
import com.spbisya.navapp.viewModels.MainViewModel
import kotlinx.coroutines.flow.collect

enum class UserDataType {
    NAME, CITY, DATE;

    val hint: String
        get() = when (this) {
            NAME -> "Введите имя"
            CITY -> "Введите город"
            DATE -> "Введите дату"
        }
}

class UserDataFragment : Fragment(R.layout.fragment_layout) {
    private val binding by viewLifecycleAware { FragmentLayoutBinding.bind(requireView()) }
    private val viewModel by viewModels<MainViewModel>(ownerProducer = { requireActivity() })

    private val userDataType by extra(extraUserDataType, UserDataType.NAME)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) = with(binding) {
        super.onViewCreated(view, savedInstanceState)
        title.text = "First fragment"
        description.text = "Args: input type = ${userDataType.name}"
        btn1.text = "Save"
        btn1.setOnClickListener {
            findNavController().navigate(R.id.thirdFragment)
            Log.e(
                "DRE",
                "backstack update, count is ${findNavController().backQueue.joinToString { it.destination.displayName }}"
            )
        }
        btn2.visibility = View.GONE
        btn3.visibility = View.GONE
        input.hint = userDataType.hint

        if (userDataType == UserDataType.NAME) {
            input.setText(viewModel.userName)
            input.doAfterTextChanged { viewModel.updateName(it?.toString()) }
        }

        initViewModel()

        return@with
    }

    private fun initViewModel() {
        // 1 launch = 1 collect MAXIMUM
        launch {
            viewModel.isEnabled.collect { isEnabled ->
                binding.btn1.isEnabled = isEnabled
            }
        }
    }

    companion object {
        private const val extraUserDataType = "userDataType"

        fun args(type: UserDataType) = bundleOf(extraUserDataType to type)
    }
}