package com.app.refine.model

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