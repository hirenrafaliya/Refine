package com.app.refine.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.app.refine.model.Article
import com.app.refine.repository.ContentRepository

class ContentViewModel : ViewModel() {
    private val TAG = "cont_vmdl_tager"
    private val repository = ContentRepository()

    fun getArticleContent(article: Article): MutableLiveData<Article> {
        return repository.getArticleContent(article)
    }

    fun getArticleContentStatus(): String = repository.getArticleContentStatus
}