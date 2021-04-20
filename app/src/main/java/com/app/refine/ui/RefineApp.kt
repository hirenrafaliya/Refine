package com.app.refine.ui

import android.app.Application
import com.app.refine.custom.InterAd
import com.app.refine.listener.OnInterAdListener
import com.app.refine.singleton.DataStoreInstance
import com.app.refine.viewmodel.ConfigViewModel
import io.realm.Realm

class RefineApp : Application() {
    private val TAG = "refn_appp_tager"

    companion object {
        lateinit var onInterAdListener: OnInterAdListener
    }

    override fun onCreate() {
        super.onCreate()

        init()
        setConfigs()

    }

    private fun init() {
        Realm.init(this)
        DataStoreInstance.init(this)
        onInterAdListener = InterAd(this)
    }

    private fun setConfigs() {
        val configViewModel = ConfigViewModel(this)

        configViewModel.setConfigDataFromDataStore()
        configViewModel.setConfigData()
    }


}