package com.app.refine.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.refine.repository.SplashRepository
import io.realm.mongodb.User
import kotlinx.coroutines.launch

class SplashViewModel : ViewModel() {

    val repository = SplashRepository()
    var user: MutableLiveData<User> = MutableLiveData()

    fun loginAnonymousUser(): MutableLiveData<User> {
        viewModelScope.launch {
            user = repository.loginAnonymousUser()
        }
        return user
    }

    fun isUserFailedToLogin(): Boolean {
        return repository.isFailedToLogin
    }
}