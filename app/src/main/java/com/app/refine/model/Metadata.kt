package com.app.refine.model

import androidx.annotation.Keep
import java.io.Serializable

@Keep
data class Metadata(
    val createdOn: String="",
    var tempId: String="",
    var isShowReviewFlow: Boolean = false
) : Serializable