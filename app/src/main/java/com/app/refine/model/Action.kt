package com.app.refine.model

import androidx.annotation.Keep
import org.bson.Document
import java.io.Serializable

@Keep
data class Action(
    val type:String="",
    val link:String="",
    val event:String=""
):Serializable