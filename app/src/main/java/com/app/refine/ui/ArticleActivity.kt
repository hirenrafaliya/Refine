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
import com.app.refine.model.Update
import com.app.refine.singleton.DataStoreInstance
import com.app.refine.utils.getLoadingAnimation
import com.app.refine.viewmodel.ArticleViewModel
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


class ArticleActivity : AppCompatActivity() {
    private val TAG = "artc_actv_tager"

    private lateinit var binding: ActivityArticleBinding
    private lateinit var viewModel: ArticleViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
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
        RefineApp.onInterAdListener.reInitInterstitialAd()
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
                        dialog.setData(update.dialogTitle, update.dialogDesc, "Update", "Later")
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