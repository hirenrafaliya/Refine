package com.app.refine.ui

import android.app.Application
import io.realm.Realm

class RefineApp : Application() {

    override fun onCreate() {
        super.onCreate()
        Realm.init(this)
    }
}