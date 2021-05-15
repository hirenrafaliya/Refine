package com.app.refine.ui

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.refine.BuildConfig
import com.app.refine.R
import com.app.refine.adapter.ArticleAdapter
import com.app.refine.custom.IosDialog
import com.app.refine.databinding.ActivityArticleBinding
import com.app.refine.model.Article
import com.app.refine.model.Config.update
import com.app.refine.utils.AdUtils
import com.app.refine.utils.Utils
import com.app.refine.utils.getLoadingAnimation
import com.app.refine.utils.toHtml
import com.app.refine.viewmodel.ArticleViewModel
import com.google.android.gms.ads.AdError
import com.google.android.gms.ads.FullScreenContentCallback
import com.skydoves.transformationlayout.onTransformationStartContainer
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class ArticleActivity : AppCompatActivity() {
    private val TAG = "artc_actv_tager"

    private lateinit var binding: ActivityArticleBinding
    private lateinit var viewModel: ArticleViewModel
    private var isLoadAd = true

    override fun onCreate(savedInstanceState: Bundle?) {
        onTransformationStartContainer()
        super.onCreate(savedInstanceState)
        binding = ActivityArticleBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModel =
            ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory(application)).get(
                ArticleViewModel::class.java
            )
        getArticleList()
        checkForUpdates()
        binding.toolbar.startAnimation(AnimationUtils.loadAnimation(this, R.anim.anim_toolbar))
    }

    private fun getArticleList() {
        binding.imgLoading.visibility = View.VISIBLE
        binding.imgLoading.startAnimation(getLoadingAnimation(this))

        viewModel.getArticle().observe(this, {
            when {
                it != null -> {
                    initRecyclerView(it)
                }
                viewModel.isGetArticleFailed() -> {
                    handleFailure()
                }
                else -> {
                    handleFailure()
                }
            }
        })
    }

    private fun initRecyclerView(articleList: MutableList<Article>) {
        binding.imgLoading.visibility = View.GONE
        binding.imgLoading.clearAnimation()
        binding.tvStatus.visibility = View.GONE

        articleList.shuffle()
        //todo : remove shuffle

        binding.recyclerView.apply {
            adapter = ArticleAdapter(articleList)
            layoutManager = LinearLayoutManager(this@ArticleActivity)
        }

        binding.recyclerView.startAnimation(
            AnimationUtils.loadAnimation(
                this,
                R.anim.anim_recycler
            )
        )
    }

    private fun handleFailure() {
        binding.imgLoading.visibility = View.INVISIBLE
        binding.tvStatus.visibility = View.VISIBLE

        binding.tvStatus.text =
            "Something went wrong\n${viewModel.getArticleError()}\nClick to retry"

        binding.tvStatus.setOnClickListener {
            binding.imgLoading.visibility = View.VISIBLE
            binding.tvStatus.visibility = View.GONE

            getArticleList()
        }
    }

    override fun onResume() {
        super.onResume()
        Log.d("inad_cstm_tager", "onResume: ")
        if (isLoadAd) RefineApp.onInterAdListener.reInitInterstitialAd()
    }

    override fun onBackPressed() {

        Utils.logRemote(
            hashMapOf(
                Pair("msg", "App exit"), Pair("tag", "app"),
                Pair(
                    "duration",
                    ((System.currentTimeMillis() - RefineApp.startTime) / 1000).toInt()
                )
            )
        )

        if (AdUtils.reqInterExit()) {
            if (RefineApp.onInterAdListener.isInterstitialAdLoaded()) {
                RefineApp.onInterAdListener.setAdListener(object : FullScreenContentCallback() {
                    override fun onAdDismissedFullScreenContent() {
                        super.onAdDismissedFullScreenContent()
                        val dialog = IosDialog(this@ArticleActivity, binding.layoutContainer)
                        dialog.setData(
                            "Exit Refine",
                            "Are you sure you want to exit ?",
                            "Exit",
                            "Cancel"
                        )
                        dialog.show()
                        dialog.setPrimaryBtnOnClickListener {
                            exit()
                        }
                    }

                    override fun onAdFailedToShowFullScreenContent(p0: AdError) {
                        super.onAdFailedToShowFullScreenContent(p0)
                        exit()
                    }
                })

                RefineApp.onInterAdListener.showInterstitialAd(this)
                isLoadAd = false
            } else {
                exit()
            }
        } else {
            exit()
        }

    }

    private fun exit() {
        try {
            finishAffinity()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun checkForUpdates() {
        GlobalScope.launch(Dispatchers.IO) {

            if (update.isShow) {
                val lVersion = update.latestVersion.replace(".", "").toInt()
                val cVersion = BuildConfig.VERSION_NAME.replace(".", "").toInt()
                //todo: change cVersion from BuildConfig bc it returns same value on different version


                if (cVersion < lVersion) {
                    launch(Dispatchers.Main) {
                        val dialog = IosDialog(this@ArticleActivity, binding.layoutContainer)
                        dialog.setData(primaryBtnText = "Update",secondaryBtnText =  "Later")
                        dialog.binding.tvTitle.text = update.dialogTitle.toHtml()
                        dialog.binding.tvDesc.text = update.dialogDesc.toHtml()
                        if (update.isForceShow) {
                            dialog.hideSecondaryBtn()
                        }
                        dialog.setIsCancelable(!update.isForceShow)
                        dialog.show()

                        dialog.setPrimaryBtnOnClickListener {
                            startActivity(
                                Intent(
                                    Intent.ACTION_VIEW,
                                    Uri.parse("market://details?id=${BuildConfig.APPLICATION_ID}")
                                )
                            )
                        }
                    }
                }
            }
        }
    }
}