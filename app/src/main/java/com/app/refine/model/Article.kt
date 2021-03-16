package com.app.refine.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable
import java.util.*


data class Article(
        val _id: DocId,
        val type: String,
        val display: Display,
        @SerializedName("content")
        val contentList: MutableList<Content>,
        val createdOn: Date,
        val ind: Int
):Serializable