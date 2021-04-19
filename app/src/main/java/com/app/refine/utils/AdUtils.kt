package com.app.refine.utils

import com.app.refine.model.Config

object AdUtils {

    private var interContentCount = 0
    fun reqInterContentCount(): Boolean {
        interContentCount++
        if (Config.interContent.isShow)
            if (interContentCount >= Config.interContent.freq) {
                interContentCount = 0
                return true
            }
        return false
    }

    private var interExitCount = 0
    fun reqInterExitCount(): Boolean {
        interExitCount++
        if (Config.interExit.isShow)
            if (interExitCount >= Config.interExit.freq) {
                interExitCount = 0
                return true
            }
        return false
    }
}