package com.app.refine.repository

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.app.refine.model.Article
import com.app.refine.utils.MongoUtils
import com.google.gson.Gson
import org.bson.Document

class ArticleRepository {
    private val TAG = "artc_repo_tager"

    var getArticleError = ""

    fun getArticle(): MutableLiveData<MutableList<Article>> {
        val articleList = MutableLiveData<MutableList<Article>>()
        val projection = Document("content", 0)

        //todo : remove limit in fut
        MongoUtils.getDatabase().getCollection("test").find().projection(projection).limit(30).iterator().getAsync {
            if (it.isSuccess) {
                Log.d(TAG, "getArticle: success")
                val list = mutableListOf<Article>()
                it.get().forEach { doc ->
                    val article = Gson().fromJson(doc.toJson(), Article::class.java)
                    list.add(article)
                }
                Log.d(TAG, "getArticle: ${list.toString()}")
                articleList.postValue(list)
            } else {
                Log.d(TAG, "getArticle: failure ${it.error}")
                getArticleError=it.error.toString()
            }
        }
        return articleList
    }
}