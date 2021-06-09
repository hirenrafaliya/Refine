package com.app.refine.ui

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.app.refine.BuildConfig
import com.app.refine.R
import com.app.refine.custom.InterAd
import com.app.refine.custom.IosDialog
import com.app.refine.databinding.ActivitySplashBinding
import com.app.refine.model.Config
import com.app.refine.singleton.DataStoreInstance
import com.app.refine.utils.Utils
import com.app.refine.utils.toHtml
import com.app.refine.viewmodel.ConfigViewModel
import com.app.refine.viewmodel.SplashViewModel
import com.google.android.gms.ads.MobileAds
import com.google.android.gms.ads.RequestConfiguration
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.messaging.FirebaseMessaging
import io.realm.Realm

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

        init()
        setConfigs()
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

                        checkForUpdates()
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

    private fun init() {
        Realm.init(this.application)
        DataStoreInstance.init(this.application)
        if (!BuildConfig.DEBUG)
            FirebaseAnalytics.getInstance(this.application)
        MobileAds.setRequestConfiguration(
            RequestConfiguration.Builder()
                .setTestDeviceIds(listOf(getString(R.string.test_device_id)))
                .build()
        )
        MobileAds.initialize(this.application)
        RefineApp.onInterAdListener = InterAd(this.application)

//        FirebaseMessaging.getInstance().subscribeToTopic("test")
        FirebaseMessaging.getInstance().subscribeToTopic("all")
        FirebaseMessaging.getInstance().subscribeToTopic("free")
        FirebaseMessaging.getInstance().subscribeToTopic(BuildConfig.VERSION_NAME)
        //todo : remove comments
    }

    private fun setConfigs() {
        val configViewModel = ConfigViewModel(this.application)

        configViewModel.setConfigDataFromDataStore()
        configViewModel.setConfigData()
    }

    private fun checkForUpdates() {
        if (Config.update.isShow) {
            val lVersion = Config.update.latestVersion.replace(".", "").toInt()
            val cVersion = BuildConfig.VERSION_NAME.replace(".", "").toInt()
            //todo: change cVersion from BuildConfig bc it returns same value on different version

            if (cVersion < lVersion) {
                val dialog = IosDialog(this@SplashActivity, binding.layoutContainer)
                dialog.setData(primaryBtnText = "Update", secondaryBtnText = "Later")
                dialog.binding.tvTitle.text = Config.update.dialogTitle.toHtml()
                dialog.binding.tvDesc.text = Config.update.dialogDesc.toHtml()
                if (Config.update.isForceShow) {
                    dialog.hideSecondaryBtn()
                }
                dialog.setIsCancelable(!Config.update.isForceShow)
                dialog.show()

                dialog.setPrimaryBtnOnClickListener {
                    startActivity(
                        Intent(
                            Intent.ACTION_VIEW,
                            Uri.parse("market://details?id=${BuildConfig.APPLICATION_ID}")
                        )
                    )
                }

                dialog.binding.tvSecondary.setOnClickListener {
                    navigateUser()
                }

            } else {
                navigateUser()
            }
        } else {
            navigateUser()
        }
    }


}

//todo: set text formatting in item_text
//todo: remove multiple launcher activities from manifest