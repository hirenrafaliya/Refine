package com.app.refine.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.app.refine.databinding.ActivitySplashBinding
import com.app.refine.utils.MongoUtils
import com.app.refine.viewmodel.SplashViewModel

class SplashActivity : AppCompatActivity() {

    private val TAG = "spls_actv_tager"

    private lateinit var binding: ActivitySplashBinding
    private lateinit var viewmodel: SplashViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewmodel =
            ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory(application)).get(
                SplashViewModel::class.java
            )

        authenticateUser()

    }

    private fun authenticateUser() {
        if (MongoUtils.getApp().currentUser() == null) {
            viewmodel.loginAnonymousUser().observe(this, Observer {
                when {
                    it != null -> {
                        startActivity(
                            Intent(
                                this@SplashActivity,
                                UploadArticleActivity::class.java
                            )
                        )
                    }
                    viewmodel.isUserFailedToLogin() -> {
                        //todo : handel failure
                    }
                    else -> {
                        //todo : handle else part same as failure
                    }
                }
            })
        } else {
            Log.d(TAG, "authenticateUser: user already authenticated")
            startActivity(
                Intent(
                    this@SplashActivity,
                    UploadArticleActivity::class.java
                )
            )
        }
    }


}