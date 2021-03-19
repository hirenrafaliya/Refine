package com.app.refine.model

import org.bson.Document
import java.io.Serializable


data class Action(
    val type:String,
    val link:String,
    val event:String
):Serializable