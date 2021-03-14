package com.app.refine.model

import org.bson.Document

data class Content(
    val type: String,
    val text: Text,
    val image: Image,
    val space: Space
)