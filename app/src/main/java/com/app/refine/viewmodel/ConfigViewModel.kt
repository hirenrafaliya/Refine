package com.app.refine.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.app.refine.repository.ConfigRepository
import kotlinx.coroutines.launch

class ConfigViewModel(application: Application) : AndroidViewModel(application) {

    val repository = ConfigRepository()

    fun setConfigData() {
        viewModelScope.launch {
            repository.setConfigData()
        }
    }

}