package com.app.refine.model

data class Image(
    var url: String,
    var marginStart: Int,
    var marginTop: Int,
    var marginEnd: Int,
    var marginBottom: Int,
    var cornerRadius: Int,
    var elevation: Int,
    val action: Action?
)