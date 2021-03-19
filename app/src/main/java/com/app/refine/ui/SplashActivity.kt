package com.app.refine.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
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
            viewmodel.loginAnonymousUser().observe(this, {
                when {
                    it != null -> {
                        startActivity(
                            Intent(
                                this@SplashActivity,
                                ArticleActivity::class.java
                            )
                        )
                        finish()
                    }
                    viewmodel.isLoginFailed() -> {
                        handleFailure()
                    }
                    else -> {
                        handleFailure()
                    }
                }
            })
        } else {
            Log.d(TAG, "authenticateUser: user already authenticated")
            startActivity(
                Intent(
                    this@SplashActivity,
                    ArticleActivity::class.java
                )
            )
            finish()
        }
    }

    private fun handleFailure() {
        binding.tvStatus.visibility = View.VISIBLE
        binding.tvStatus.text = "Something went wrong\n${viewmodel.getLoginError()}\nClick to retry"

        binding.tvStatus.setOnClickListener {
            binding.tvStatus.text="Loading..."
            authenticateUser()
        }
    }


}

//todo: set text formatting in item_text