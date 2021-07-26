package com.app.refine.repository

import android.util.Log
import com.app.refine.model.Article
import com.app.refine.utils.MongoUtils
import com.google.gson.Gson
import com.google.gson.JsonObject
import org.bson.Document
import org.json.JSONObject

class UploadRepository {
    private val TAG = "upld_repo_tager"

    fun uploadArticle(article: Article) {

        val gson=Gson()
        gson.serializeNulls()
        val jsonObject=JSONObject(gson.toJson(article))
        jsonObject.remove("_id")
        MongoUtils
            .getDatabase()
            .getCollection("articles")
            .insertOne(Document.parse(jsonObject.toString()))
            .getAsync {
                if (it.isSuccess) {
                    Log.d(TAG, "uploadArticle: success")
                } else {
                    Log.d(TAG, "uploadArticle: failure: ${it.error}")
                }
            }
    }
}