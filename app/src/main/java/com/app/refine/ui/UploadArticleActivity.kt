package com.app.refine.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.refine.adapter.ContentAdapter
import com.app.refine.databinding.ActivityUploadArticleBinding
import com.app.refine.model.Content
import com.app.refine.viewmodel.UploadArticleViewModel

class UploadArticleActivity : AppCompatActivity() {
    private val TAG = "uart_actv_tager"

    private lateinit var binding: ActivityUploadArticleBinding
    private lateinit var viewModel: UploadArticleViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUploadArticleBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModel =
            ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory(application)).get(
                UploadArticleViewModel::class.java
            )

        getArticleList()
    }

    private fun getArticleList() {
        viewModel.getArticle().observe(this, Observer {
            if (!viewModel.isFailedToGetArticle()) {
                initRecyclerView(it[0].contentList)
            } else {
                //todo: handle failure
            }
        })
    }

    private fun initRecyclerView(contentList: MutableList<Content>) {
        binding.recyclerView.apply {
            adapter=ContentAdapter(contentList)
            layoutManager=LinearLayoutManager(this@UploadArticleActivity)
        }
    }
}