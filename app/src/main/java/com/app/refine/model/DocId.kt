package com.app.refine.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class DocId(
    @SerializedName("\$oid")
    val id: String
) : Serializable