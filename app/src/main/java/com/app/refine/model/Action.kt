package com.app.refine.model

import org.bson.Document


data class Action(
    val type:String,
    val link:String,
    val event:String
)