package com.app.refine.ui

import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.refine.R
import com.app.refine.adapter.ArticleAdapter
import com.app.refine.databinding.ActivityArticleBinding
import com.app.refine.model.Article
import com.app.refine.utils.getLoadingAnimation
import com.app.refine.viewmodel.ArticleViewModel

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
}