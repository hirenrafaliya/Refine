package com.app.refine.model

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
    val width: String,
    val horizontalBias: Float,
    val action: Action,
    val background: Background
)