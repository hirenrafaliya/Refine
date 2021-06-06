package com.app.refine.model

import androidx.annotation.Keep
import java.io.Serializable

@Keep
data class Content(
    var type: String="",
    val text: Text= Text(),
    val image: Image?= Image(),
    val space: Space?=Space()
):Serializable