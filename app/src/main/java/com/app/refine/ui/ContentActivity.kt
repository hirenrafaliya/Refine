package com.app.refine.ui

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.app.refine.databinding.ActivityContentBinding
import com.app.refine.model.Article
import com.app.refine.utils.isFailed
import com.app.refine.utils.isSuccess
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
        article = intent.getSerializableExtra("article") as Article

        viewModel.getArticleContent(article).observe(this, {
            if (viewModel.getArticleContentStatus().isSuccess()) {
                Log.d(TAG, "getArticleContent: $it")
            } else if (viewModel.getArticleContentStatus().isFailed()) {
                handleFailure()
            }
        })
    }

    private fun handleFailure() {
        
    }
}