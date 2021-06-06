package com.app.refine.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.refine.R
import com.app.refine.adapter.ContentAdapter
import com.app.refine.databinding.ActivityContentBinding
import com.app.refine.model.Article
import com.app.refine.utils.*
import com.app.refine.viewmodel.ContentViewModel

class ContentActivity : AppCompatActivity() {
    private val TAG = "cont_actv_tager"
    private lateinit var viewModel: ContentViewModel
    private lateinit var binding: ActivityContentBinding
    private lateinit var article: Article
    private val startTime = System.currentTimeMillis()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityContentBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModel =
            ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory(application)).get(
                ContentViewModel::class.java
            )
        RefineApp.onInterAdListener.reInitInterstitialAd()

        if (AdUtils.reqInterContent()) {
            RefineApp.onInterAdListener.showInterstitialAd(this)
        }

        getArticleContent()
    }

    private fun getArticleContent() {
        binding.imgLoading.visibility = View.VISIBLE
        binding.imgLoading.startAnimation(getLoadingAnimation(this))

        article = (intent.getSerializableExtra("article") ?: Article()) as Article
        if (article._id.id.isBlank()) {
            article._id.id = intent.getStringExtra("articleId") ?: ""
        }

        Utils.logRemote(
            hashMapOf(
                Pair("msg", "Article open"),
                Pair("articleId", article._id.id ?: "null"),
                Pair("tag", "article")
            )
        )

        viewModel.getArticleContent(article).observe(this, {
            if (viewModel.getArticleContentStatus().status.isSuccess()) {
                article = it
                setArticleContent()
            } else if (viewModel.getArticleContentStatus().status.isFailed()) {
                handleFailure()
            }
        })
    }

    private fun handleFailure() {
        binding.imgLoading.visibility = View.INVISIBLE
        binding.imgLoading.clearAnimation()
        binding.tvStatus.visibility = View.VISIBLE

        binding.tvStatus.text =
            "Something went wrong\n${viewModel.getArticleContentStatus().error}\nClick to retry"

        binding.tvStatus.setOnClickListener {
            binding.imgLoading.visibility = View.VISIBLE
            binding.tvStatus.visibility = View.GONE

            getArticleContent()
        }
    }

    private fun setArticleContent() {
        binding.tvTitle.text = article.display.title.toHtml()
        binding.tvTitle.isSelected = true

        binding.imgBack.setOnClickListener {
            onBackPressed()
        }

        initRecyclerView()

        viewModel.setReviewFlow(this,article._id.id,article.metadata.isShowReviewFlow)

    }

    private fun initRecyclerView() {
        binding.imgLoading.visibility = View.GONE
        binding.imgLoading.clearAnimation()
        binding.tvStatus.visibility = View.GONE


        binding.recyclerView.apply {
            adapter = ContentAdapter(article.contentList)
            layoutManager = LinearLayoutManager(this@ContentActivity)
        }

        binding.scrollView.startAnimation(
            AnimationUtils.loadAnimation(
                this,
                R.anim.anim_recycler
            )
        )
    }

    override fun onBackPressed() {
        super.onBackPressed()

        Utils.logRemote(
            hashMapOf(
                Pair("msg", "Article exit"),
                Pair("articleId", article._id.id ?: "null"),
                Pair("tag", "article"),
                Pair("duration", ((System.currentTimeMillis() - startTime) / 1000).toInt())
            )
        )

        if (intent.getStringExtra("from") == "ArticleActivity") {
            super.onBackPressed()
        } else {
            startActivity(Intent(this, ArticleActivity::class.java))
            finish()
        }
    }
}