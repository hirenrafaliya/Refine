package com.app.refine.model

import org.bson.Document

data class Display(
    val title:String,
    val description:String,
    val img:String,
) :Document()