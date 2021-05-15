package com.app.refine.model

import androidx.annotation.Keep
import org.bson.Document
import java.io.Serializable

@Keep
data class Display(
    var title:String,
    var description:String,
    var img:String,
):Serializable