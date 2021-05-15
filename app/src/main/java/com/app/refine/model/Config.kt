package com.app.refine.model

import androidx.annotation.Keep

@Keep
object Config {
    var update: Update = Update()
    var interContent: InterContent = InterContent()
    var interExit: InterExit = InterExit()
}

@Keep
data class InterExit(
    val freq: Int = 1,
    @JvmField
    val isShow: Boolean = false,
    var updatedOn: String = "0"
)

@Keep
data class InterContent(
    val freq: Int = 3,
    @JvmField
    val isShow: Boolean = false,
    var updatedOn: String = "0"
)

@Keep
data class Update(
    var dialogTitle: String = "",
    var dialogDesc: String = "",
    @JvmField
    var isForceShow: Boolean = false,
    @JvmField
    var isShow: Boolean = false,
    var latestVersion: String = "0",
    var updatedOn: String = "0"
)