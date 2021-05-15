package com.app.refine.model

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
import java.io.Serializable


@Keep
data class Article(
    val _id: DocId?,
    var type: String,
    val display: Display,
    @SerializedName("contents")
        val contentList: MutableList<Content>,
    var ind: Int,
    val metadata: Metadata
):Serializable

//todo : changed content => contents