package com.app.refine.utils

import android.content.Context
import android.text.Spanned
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.core.text.HtmlCompat
import com.app.refine.R

fun getLoadingAnimation(context: Context): Animation? {
    return AnimationUtils.loadAnimation(context, R.anim.anim_loading)
}

fun String.toHtml(): Spanned {
    return HtmlCompat.fromHtml(this, HtmlCompat.FROM_HTML_MODE_LEGACY)
}

fun String.isSuccess(): Boolean {
    return this == "success"
}

fun String.isFailed(): Boolean {
    return this != "success" && this != ""
}