package com.app.refine.model

import androidx.annotation.Keep
import java.io.Serializable

@Keep
data class Background(
    val cornerRadius: Int,
    val solidColor: String,
    val strokeColor: String,
    val strokeWidth: Int,
    val isRipple: Boolean,
    val rippleColor: String
) : Serializable