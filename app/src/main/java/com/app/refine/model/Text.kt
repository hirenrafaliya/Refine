package com.app.refine.model

data class Text(
    var text: String,
    var size: Int,
    var color: String,
    var alignment: String,
    var font: String,
    var marginStart: Int,
    var marginTop: Int,
    var marginEnd: Int,
    var marginBottom: Int,
    var paddingStart: Int,
    var paddingTop: Int,
    var paddingEnd: Int,
    var paddingBottom: Int,
    var width: String,
    var horizontalBias: Float,
    var action: Action?,
    var background: Background?
)