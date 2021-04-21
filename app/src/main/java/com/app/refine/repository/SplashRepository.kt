package com.app.refine.repository

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.app.refine.utils.MongoUtils
import io.realm.mongodb.Credentials
import io.realm.mongodb.User

class SplashRepository {

    private val TAG = "spls_repo_tager"

    var loginError = ""

    fun loginAnonymousUser(): MutableLiveData<User> {
        val user: MutableLiveData<User> = MutableLiveData()
        MongoUtils.getApp().loginAsync(Credentials.anonymous()) {
            if (it.isSuccess) {
                Log.d(TAG, "loginAnonymousUser: success")
                user.postValue(it.get())
            } else {
                Log.d(TAG, "loginAnonymousUser: failure : ${it.error}")
                user.postValue(null)
                loginError = it.error.toString()
            }
        }
        return user
    }
}