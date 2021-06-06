package com.app.refine.model

import androidx.annotation.Keep
import java.io.Serializable

@Keep
data class Text(
    var text: String="",
    var size: Int=0,
    var color: String="",
    var alignment: String="",
    var font: String="",
    var marginStart: Int=0,
    var marginTop: Int=0,
    var marginEnd: Int=0,
    var marginBottom: Int=0,
    var paddingStart: Int=0,
    var paddingTop: Int=0,
    var paddingEnd: Int=0,
    var paddingBottom: Int=0,
    var width: String="",
    var horizontalBias: Float=0f,
    var action: Action?= Action(),
    var background: Background?=Background()
) : Serializable