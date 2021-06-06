package com.app.refine.viewmodel

import android.app.Activity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.app.refine.BuildConfig
import com.app.refine.model.Article
import com.app.refine.model.Status
import com.app.refine.repository.ContentRepository
import com.google.android.play.core.review.ReviewInfo
import com.google.android.play.core.review.ReviewManagerFactory
import com.google.android.play.core.tasks.Task
import com.google.firebase.analytics.FirebaseAnalytics

class ContentViewModel : ViewModel() {
    private val TAG = "cont_vmdl_tager"
    private val repository = ContentRepository()

    fun getArticleContent(article: Article): MutableLiveData<Article> {
        return repository.getArticleContent(article)
    }

    fun getArticleContentStatus(): Status = repository.getArticleContentStatus

    fun setReviewFlow(activity: Activity, article: String, isShowReviewFlow: Boolean) {
        Log.d(TAG, "setReviewFlow: ")
        if (isShowReviewFlow) {
            val manager = ReviewManagerFactory.create(activity)
            val request: Task<ReviewInfo?> = manager.requestReviewFlow()
            request.addOnCompleteListener { task: Task<ReviewInfo?> ->
                if (task.isSuccessful) {
                    val reviewInfo = task.result
                    val flow: Task<Void> = manager.launchReviewFlow(activity, reviewInfo)
                    flow.addOnCompleteListener {
                        try {
                            val bundle = Bundle()
                            bundle.putString(FirebaseAnalytics.Param.ITEM_ID, article)
                            bundle.putString(FirebaseAnalytics.Param.CONTENT_TYPE, "review")
                            if (!BuildConfig.DEBUG)
                                FirebaseAnalytics.getInstance(activity.applicationContext)
                                    .logEvent("review_impression", bundle)
                            Log.d(TAG, "onComplete: Review flow")
                        } catch (e: Exception) {
                        }
                    }
                } else {
                    Log.d(TAG, "showReviewFlow: task.unsuccessfull")
                }
            }
        }
    }
}