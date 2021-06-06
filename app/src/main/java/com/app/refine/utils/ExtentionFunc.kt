package com.app.refine.utils

import android.content.Context
import android.graphics.Color
import android.graphics.Typeface
import android.text.Spanned
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.TextView
import androidx.core.content.res.ResourcesCompat
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

fun Int.toSdp(context: Context): Float {
    return when (this) {
        0 -> 0f
        1 -> context.resources.getDimension(R.dimen._1sdp)
        2 -> context.resources.getDimension(R.dimen._2sdp)
        3 -> context.resources.getDimension(R.dimen._3sdp)
        4 -> context.resources.getDimension(R.dimen._4sdp)
        5 -> context.resources.getDimension(R.dimen._5sdp)
        6 -> context.resources.getDimension(R.dimen._6sdp)
        7 -> context.resources.getDimension(R.dimen._7sdp)
        8 -> context.resources.getDimension(R.dimen._8sdp)
        9 -> context.resources.getDimension(R.dimen._9sdp)
        10 -> context.resources.getDimension(R.dimen._10sdp)
        11 -> context.resources.getDimension(R.dimen._11sdp)
        12 -> context.resources.getDimension(R.dimen._12sdp)
        13 -> context.resources.getDimension(R.dimen._13sdp)
        14 -> context.resources.getDimension(R.dimen._14sdp)
        15 -> context.resources.getDimension(R.dimen._15sdp)
        16 -> context.resources.getDimension(R.dimen._16sdp)
        17 -> context.resources.getDimension(R.dimen._17sdp)
        18 -> context.resources.getDimension(R.dimen._18sdp)
        19 -> context.resources.getDimension(R.dimen._19sdp)
        20 -> context.resources.getDimension(R.dimen._20sdp)
        21 -> context.resources.getDimension(R.dimen._21sdp)
        22 -> context.resources.getDimension(R.dimen._22sdp)
        23 -> context.resources.getDimension(R.dimen._23sdp)
        24 -> context.resources.getDimension(R.dimen._24sdp)
        25 -> context.resources.getDimension(R.dimen._25sdp)
        26 -> context.resources.getDimension(R.dimen._26sdp)
        27 -> context.resources.getDimension(R.dimen._27sdp)
        28 -> context.resources.getDimension(R.dimen._28sdp)
        29 -> context.resources.getDimension(R.dimen._29sdp)
        30 -> context.resources.getDimension(R.dimen._30sdp)
        31 -> context.resources.getDimension(R.dimen._31sdp)
        32 -> context.resources.getDimension(R.dimen._32sdp)
        33 -> context.resources.getDimension(R.dimen._33sdp)
        34 -> context.resources.getDimension(R.dimen._34sdp)
        35 -> context.resources.getDimension(R.dimen._35sdp)
        36 -> context.resources.getDimension(R.dimen._36sdp)
        37 -> context.resources.getDimension(R.dimen._37sdp)
        38 -> context.resources.getDimension(R.dimen._38sdp)
        39 -> context.resources.getDimension(R.dimen._39sdp)
        40 -> context.resources.getDimension(R.dimen._40sdp)
        41 -> context.resources.getDimension(R.dimen._41sdp)
        42 -> context.resources.getDimension(R.dimen._42sdp)
        43 -> context.resources.getDimension(R.dimen._43sdp)
        44 -> context.resources.getDimension(R.dimen._44sdp)
        45 -> context.resources.getDimension(R.dimen._45sdp)
        46 -> context.resources.getDimension(R.dimen._46sdp)
        47 -> context.resources.getDimension(R.dimen._47sdp)
        48 -> context.resources.getDimension(R.dimen._48sdp)
        49 -> context.resources.getDimension(R.dimen._49sdp)
        50 -> context.resources.getDimension(R.dimen._50sdp)
        51 -> context.resources.getDimension(R.dimen._51sdp)
        52 -> context.resources.getDimension(R.dimen._52sdp)
        53 -> context.resources.getDimension(R.dimen._53sdp)
        54 -> context.resources.getDimension(R.dimen._54sdp)
        55 -> context.resources.getDimension(R.dimen._55sdp)
        56 -> context.resources.getDimension(R.dimen._56sdp)
        57 -> context.resources.getDimension(R.dimen._57sdp)
        58 -> context.resources.getDimension(R.dimen._58sdp)
        59 -> context.resources.getDimension(R.dimen._59sdp)
        60 -> context.resources.getDimension(R.dimen._60sdp)
        61 -> context.resources.getDimension(R.dimen._61sdp)
        62 -> context.resources.getDimension(R.dimen._62sdp)
        63 -> context.resources.getDimension(R.dimen._63sdp)
        64 -> context.resources.getDimension(R.dimen._64sdp)
        65 -> context.resources.getDimension(R.dimen._65sdp)
        66 -> context.resources.getDimension(R.dimen._66sdp)
        67 -> context.resources.getDimension(R.dimen._67sdp)
        68 -> context.resources.getDimension(R.dimen._68sdp)
        69 -> context.resources.getDimension(R.dimen._69sdp)
        70 -> context.resources.getDimension(R.dimen._70sdp)
        71 -> context.resources.getDimension(R.dimen._71sdp)
        72 -> context.resources.getDimension(R.dimen._72sdp)
        73 -> context.resources.getDimension(R.dimen._73sdp)
        74 -> context.resources.getDimension(R.dimen._74sdp)
        75 -> context.resources.getDimension(R.dimen._75sdp)
        76 -> context.resources.getDimension(R.dimen._76sdp)
        77 -> context.resources.getDimension(R.dimen._77sdp)
        78 -> context.resources.getDimension(R.dimen._78sdp)
        79 -> context.resources.getDimension(R.dimen._79sdp)
        80 -> context.resources.getDimension(R.dimen._80sdp)
        81 -> context.resources.getDimension(R.dimen._81sdp)
        82 -> context.resources.getDimension(R.dimen._82sdp)
        83 -> context.resources.getDimension(R.dimen._83sdp)
        84 -> context.resources.getDimension(R.dimen._84sdp)
        85 -> context.resources.getDimension(R.dimen._85sdp)
        86 -> context.resources.getDimension(R.dimen._86sdp)
        87 -> context.resources.getDimension(R.dimen._87sdp)
        88 -> context.resources.getDimension(R.dimen._88sdp)
        89 -> context.resources.getDimension(R.dimen._89sdp)
        90 -> context.resources.getDimension(R.dimen._90sdp)
        91 -> context.resources.getDimension(R.dimen._91sdp)
        92 -> context.resources.getDimension(R.dimen._92sdp)
        93 -> context.resources.getDimension(R.dimen._93sdp)
        94 -> context.resources.getDimension(R.dimen._94sdp)
        95 -> context.resources.getDimension(R.dimen._95sdp)
        96 -> context.resources.getDimension(R.dimen._96sdp)
        97 -> context.resources.getDimension(R.dimen._97sdp)
        98 -> context.resources.getDimension(R.dimen._98sdp)
        99 -> context.resources.getDimension(R.dimen._99sdp)
        100 -> context.resources.getDimension(R.dimen._100sdp)
        else -> return context.resources.getDimension(R.dimen._14sdp)
    }
}

fun String.toColor(): Int {
    return if (this.isNotBlank())
        Color.parseColor(this)
    else
        Color.parseColor("#434343")
}

fun String.toAlignment(): Int {
    return when (this) {
        "center" -> TextView.TEXT_ALIGNMENT_CENTER
        "textEnd" -> TextView.TEXT_ALIGNMENT_TEXT_END
        "textStart" -> TextView.TEXT_ALIGNMENT_TEXT_START
        "viewEnd" -> TextView.TEXT_ALIGNMENT_VIEW_END
        "viewStart" -> TextView.TEXT_ALIGNMENT_VIEW_START
        "gravity" -> TextView.TEXT_ALIGNMENT_GRAVITY
        "inherit" -> TextView.TEXT_ALIGNMENT_INHERIT
        else -> TextView.TEXT_ALIGNMENT_TEXT_START
    }
}

fun String.toTypeFace(context: Context): Typeface? {
    return when (this) {
        "bold" -> ResourcesCompat.getFont(context,R.font.ps_bold)
        "medium" -> ResourcesCompat.getFont(context,R.font.ps_bold)
        "regular" -> ResourcesCompat.getFont(context,R.font.ps_regular)
        else -> ResourcesCompat.getFont(context,R.font.ps_regular)
    }

}