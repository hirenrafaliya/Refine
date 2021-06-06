package com.app.refine.model

import androidx.annotation.Keep
import java.io.Serializable

@Keep
data class Background(
    val cornerRadius: Int=0,
    val solidColor: String="",
    val strokeColor: String="",
    val strokeWidth: Int=0,
    val isRipple: Boolean=false,
    val rippleColor: String=""
) : Serializable