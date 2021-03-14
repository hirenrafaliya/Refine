package com.app.refine.model

import org.bson.Document

data class Text(
    val text: String,
    val size: Int,
    val color: String,
    val alignment: String,
    val font: String,
    val marginStart: Int,
    val marginTop: Int,
    val marginEnd: Int,
    val marginBottom: Int,
    val paddingStart: Int,
    val paddingTop: Int,
    val paddingEnd: Int,
    val paddingBottom: Int,
    val action:Action
)