package com.app.refine.model

import java.io.Serializable

data class Background(
    val cornerRadius: Int,
    val solidColor: String,
    val strokeColor: String,
    val strokeWidth: Int,
    val isRipple: Boolean,
    val rippleColor: String
) : Serializable