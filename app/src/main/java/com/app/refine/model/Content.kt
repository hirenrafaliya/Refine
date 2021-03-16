package com.app.refine.model

import org.bson.Document
import java.io.Serializable

data class Content(
    val type: String,
    val text: Text,
    val image: Image,
    val space: Space
):Serializable