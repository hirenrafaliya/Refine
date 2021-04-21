package com.app.refine.repository

import android.util.Log
import com.app.refine.model.Article
import com.app.refine.utils.MongoUtils
import com.google.gson.Gson
import org.bson.Document

class UploadRepository {
    private val TAG = "upld_repo_tager"

    fun uploadArticle(article: Article) {

        val gson=Gson()
        gson.serializeNulls()
        MongoUtils
            .getDatabase()
            .getCollection("articles")
            .insertOne(Document.parse(gson.toJson(article)))
            .getAsync {
                if (it.isSuccess) {
                    Log.d(TAG, "uploadArticle: success")
                } else {
                    Log.d(TAG, "uploadArticle: failure: ${it.error}")
                }
            }
    }
}