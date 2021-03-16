package com.app.refine.model

import org.bson.Document
import java.io.Serializable

data class Display(
    val title:String,
    val description:String,
    val img:String,
):Serializable