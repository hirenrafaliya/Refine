package com.app.refine.repository

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.app.refine.model.Article
import com.app.refine.model.Status
import com.app.refine.utils.MongoUtils
import com.google.gson.Gson
import org.bson.Document
import org.bson.types.ObjectId

class ContentRepository {
    private val TAG = "cont_repo_tager"

    var getArticleContentStatus = Status()

    fun getArticleContent(article: Article): MutableLiveData<Article> {
        val articleLiveData = MutableLiveData<Article>()

        Log.d(TAG, "_id:${article._id}")
        val query = Document("_id",ObjectId(article._id.id))
        MongoUtils.getDatabase().getCollection("articles").findOne(query)
            .getAsync {
                if (it.isSuccess) {
                    Log.d(TAG, "getArticleContent: success")
                    getArticleContentStatus.status = "success"
                    val art = Gson().fromJson(it.get().toJson(), Article::class.java)
                    articleLiveData.postValue(art)
                } else {
                    Log.d(TAG, "getArticleContent: failure ${it.error}")
                    getArticleContentStatus.status = "failure"
                    getArticleContentStatus.error = it.error.toString()
                    articleLiveData.postValue(Article())
                }
            }
        return articleLiveData
    }
}