package com.app.refine.ui

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
import com.app.refine.utils.getLoadingAnimation
import com.app.refine.utils.isFailed
import com.app.refine.utils.isSuccess
import com.app.refine.utils.toHtml
import com.app.refine.viewmodel.ContentViewModel

class ContentActivity : AppCompatActivity() {
    private val TAG = "cont_actv_tager"
    private lateinit var viewModel: ContentViewModel
    private lateinit var binding: ActivityContentBinding
    private lateinit var article: Article

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityContentBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModel =
            ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory(application)).get(
                ContentViewModel::class.java
            )

        getArticleContent()
    }

    private fun getArticleContent() {
        binding.imgLoading.visibility = View.VISIBLE
        binding.imgLoading.startAnimation(getLoadingAnimation(this))

        article = intent.getSerializableExtra("article") as Article

        viewModel.getArticleContent(article).observe(this, {
            if (viewModel.getArticleContentStatus().isSuccess()) {
                article = it
                setArticleContent()
            } else if (viewModel.getArticleContentStatus().isFailed()) {
                handleFailure()
            }
        })
    }

    private fun handleFailure() {
        binding.imgLoading.visibility = View.INVISIBLE
        binding.tvStatus.visibility = View.VISIBLE

        binding.tvStatus.text =
            "Something went wrong\n${viewModel.getArticleContentStatus()}\nClick to retry"

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
}