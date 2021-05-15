package com.app.refine.model

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
import java.io.Serializable

@Keep
data class DocId(
    @SerializedName("\$oid")
    val id: String
) : Serializable