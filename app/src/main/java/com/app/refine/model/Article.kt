package com.app.refine.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable
import java.util.*


data class Article(
        val _id: DocId?,
        var type: String,
        val display: Display,
        @SerializedName("contents")
        val contentList: MutableList<Content>,
        val ind: Int,
        val metadata: Metadata
):Serializable

//todo : changed content => contents