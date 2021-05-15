package com.app.refine.model

import androidx.annotation.Keep
import org.bson.Document
import java.io.Serializable

@Keep
data class Space(
    val height: Int,
):Serializable