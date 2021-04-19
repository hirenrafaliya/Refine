package com.app.refine.ui

import android.app.Application
import com.app.refine.singleton.DataStoreInstance
import com.app.refine.viewmodel.ConfigViewModel
import io.realm.Realm

class RefineApp : Application() {
    private val TAG = "refn_appp_tager"

    override fun onCreate() {
        super.onCreate()

        init()
        setConfigs()

    }

    private fun init() {
        Realm.init(this)
        DataStoreInstance.init(this)
    }

    private fun setConfigs() {
        val configViewModel = ConfigViewModel(this)

        configViewModel.setConfigDataFromDataStore()
        configViewModel.setConfigData()

    }
}