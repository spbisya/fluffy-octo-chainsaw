package com.spbisya.navapp.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {

    var userName: String = ""
        private set

    private val _isEnabled = MutableStateFlow(false)
    val isEnabled = _isEnabled.asStateFlow()

    fun updateName(name: String?) {
        viewModelScope.launch {
            userName = name ?: ""
            _isEnabled.emit(userName.length > 3)
        }
    }
}