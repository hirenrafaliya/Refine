package com.app.refine.model

import org.bson.Document
import java.io.Serializable

data class Display(
    var title:String,
    var description:String,
    var img:String,
):Serializable