package com.app.refine.model

import org.bson.Document
import org.bson.codecs.pojo.annotations.BsonProperty
import org.bson.types.ObjectId
import java.util.*

data class Article(
        val _id: ObjectId,
        val type: String,
        val display: Display,
        @BsonProperty("content")
        val contentList: List<Content>,
        val createdOn: Date,
        val ind: Int
) : Document()