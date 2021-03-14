package com.app.refine.model

import com.google.gson.annotations.SerializedName
import org.bson.types.ObjectId
import java.util.*

data class Article(
        val _id: ObjectId,
        val type: String,
        val display: Display,
        @SerializedName("content")
        val contentList: MutableList<Content>,
        val createdOn: Date,
        val ind: Int
)