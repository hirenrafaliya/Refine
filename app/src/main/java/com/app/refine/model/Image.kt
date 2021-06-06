package com.app.refine.model

import androidx.annotation.Keep
import java.io.Serializable

@Keep
data class Image(
    var url: String="",
    var marginStart: Int=0,
    var marginTop: Int=0,
    var marginEnd: Int=0,
    var marginBottom: Int=0,
    var cornerRadius: Int=0,
    var elevation: Int=0,
    val action: Action?=Action()
): Serializable