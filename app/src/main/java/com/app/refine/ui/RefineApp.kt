package com.app.refine.ui

import android.app.Application
import com.app.refine.singleton.DataStoreInstance
import io.realm.Realm

class RefineApp : Application() {

    override fun onCreate() {
        super.onCreate()
        Realm.init(this)
        DataStoreInstance.init(this)
    }
}