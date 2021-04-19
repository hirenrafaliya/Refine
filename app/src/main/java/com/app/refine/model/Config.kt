package com.app.refine.model

object Config {
    var update: Update = Update()
    var interContent: InterContent = InterContent()
    var interExit: InterExit = InterExit()
}

data class InterExit(
    val freq: Int = 1,
    @JvmField
    val isShow: Boolean = true,
    var updatedOn: String = "0"
)

data class InterContent(
    val freq: Int = 1,
    @JvmField
    val isShow: Boolean = true,
    var updatedOn: String = "0"
)

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