package com.app.refine.model

import java.io.Serializable

data class Content(
    var type: String,
    val text: Text?,
    val image: Image?,
    val space: Space?
):Serializable