package com.app.refine.model

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
import java.io.Serializable


@Keep
data class Article(
    var _id: DocId = DocId(),
    var type: String = "",
    var display: Display = Display(),
    var ind: Int = 0,
    var metadata: Metadata = Metadata(),
    @SerializedName("contents")
    var contentList: MutableList<Content> = mutableListOf<Content>(),
):Serializable

//todo : changed content => contents