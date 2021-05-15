package com.app.refine.model

import androidx.annotation.Keep
import java.io.Serializable

@Keep
data class Image(
    var url: String,
    var marginStart: Int,
    var marginTop: Int,
    var marginEnd: Int,
    var marginBottom: Int,
    var cornerRadius: Int,
    var elevation: Int,
    val action: Action?
): Serializable