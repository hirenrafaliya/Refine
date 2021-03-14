package com.app.refine.repository

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.app.refine.model.Article
import com.app.refine.utils.MongoUtils
import com.google.gson.Gson

class ArticleRepository {
    private val TAG = "artc_repo_tager"

    var isFailedToGetArticle = false

    fun getArticle(): MutableLiveData<MutableList<Article>> {
        val articleList = MutableLiveData<MutableList<Article>>()
        MongoUtils.getDatabase().getCollection("test").find().iterator().getAsync {
            if (it.isSuccess) {
                Log.d(TAG, "getArticle: success")
                val list = mutableListOf<Article>()
                it.get().forEach { doc ->
                    val article = Gson().fromJson(doc.toJson(), Article::class.java)
                    list.add(article)
                }
                articleList.postValue(list)
            } else {
                Log.d(TAG, "getArticle: failure ${it.error}")
                isFailedToGetArticle = true
            }
        }
        return articleList
    }
}