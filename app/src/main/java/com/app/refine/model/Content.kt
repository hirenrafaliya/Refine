package com.app.refine.model

import androidx.annotation.Keep
import java.io.Serializable

@Keep
data class Content(
    var type: String,
    val text: Text?,
    val image: Image?,
    val space: Space?
):Serializable