package com.app.refine.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.refine.model.Article
import com.app.refine.repository.ArticleRepository
import kotlinx.coroutines.launch

class UploadArticleViewModel : ViewModel() {
    private val TAG = "uart_vmdl_tager"

    private val repository = ArticleRepository()
    private var articleList = MutableLiveData<MutableList<Article>>()

    fun getArticle(): MutableLiveData<MutableList<Article>> {
        viewModelScope.launch {
            articleList = repository.getArticle()
        }
        return articleList
    }

    fun isFailedToGetArticle():Boolean=repository.isFailedToGetArticle
}