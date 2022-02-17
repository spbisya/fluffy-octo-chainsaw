package com.spbisya.navapp

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.NavOptions
import androidx.navigation.fragment.NavHostFragment
import com.spbisya.navapp.R
import com.spbisya.navapp.databinding.ActivityMainBinding
import com.spbisya.navapp.fragments.UserDataFragment
import com.spbisya.navapp.fragments.UserDataType

class MainActivity : AppCompatActivity() {
    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController

        binding.first.setOnClickListener { navigateTo(navController, UserDataType.NAME) }
        binding.second.setOnClickListener { navigateTo(navController, UserDataType.CITY) }
    }

    private fun navigateTo(navController: NavController, userDataType: UserDataType) {
        navController.navigate(
            resId = R.id.firstFragment,
            args = UserDataFragment.args(userDataType),
            navOptions = NavOptions.Builder().setPopUpTo(R.id.firstFragment, true).build()
        )
        Log.e("LOG", "error")
    }
}