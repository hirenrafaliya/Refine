package com.app.refine.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.app.refine.databinding.ActivitySplashBinding
import com.app.refine.utils.Utils
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
            viewmodel.loginAnonymousUser().observe(this, {
                when {
                    it != null -> {
                        Log.d(TAG, "authenticateUser: accessToken=${it.accessToken}")
                        Log.d(TAG, "authenticateUser: refreshToken=${it.refreshToken}")
                        Log.d(TAG, "authenticateUser: id=${it.id}")

                        Utils.logRemote(hashMapOf(Pair("msg", "App open"), Pair("tag", "app")))

                        navigateUser()
                    }
                    viewmodel.isLoginFailed() -> {
                        handleFailure()
                    }
                    else -> {
                        handleFailure()
                    }
                }
            })
    }

    private fun navigateUser() {
        val open = intent.getStringExtra("open")
        val articleId = intent.getStringExtra("articleId")

        Log.d(TAG, "navigateUser: $open $articleId")

        if (open.isNullOrBlank() || articleId.isNullOrBlank() || open.isNullOrEmpty() || articleId.isNullOrEmpty()) {
            Log.d(TAG, "navigateUser: null null null null")
            startActivity(
                Intent(
                    this@SplashActivity,
                    ArticleActivity::class.java
                )
            )
        } else {
            if (open == "ContentActivity" && !articleId.isNullOrBlank() && !articleId.isNullOrEmpty()) {
                Log.d(TAG, "navigateUser: ContentActivity")
                val intent = Intent(this@SplashActivity, ContentActivity::class.java)
                intent.putExtra("articleId", articleId)
                startActivity(intent)
            } else if (open == "ArticleActivity") {
                Log.d(TAG, "navigateUser: ArticleActivity")
                startActivity(Intent(this@SplashActivity, ArticleActivity::class.java))
            } else {
                Log.d(TAG, "navigateUser: elsee")
                startActivity(
                    Intent(
                        this@SplashActivity,
                        ArticleActivity::class.java
                    )
                )
            }
        }
        finish()
    }

    private fun handleFailure() {
        binding.tvStatus.visibility = View.VISIBLE
        binding.tvStatus.text = "Something went wrong\n${viewmodel.getLoginError()}\nClick to retry"

        binding.tvStatus.setOnClickListener {
            binding.tvStatus.text = "Loading..."
            authenticateUser()
        }
    }

}

//todo: set text formatting in item_text
//todo: remove multiple launcher activities from manifest