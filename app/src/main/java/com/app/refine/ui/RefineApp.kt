package com.app.refine.ui

import android.app.Application
import com.app.refine.R
import com.app.refine.custom.InterAd
import com.app.refine.listener.OnInterAdListener
import com.app.refine.singleton.DataStoreInstance
import com.app.refine.utils.Utils
import com.app.refine.viewmodel.ConfigViewModel
import com.google.android.gms.ads.MobileAds
import com.google.android.gms.ads.RequestConfiguration
import com.google.firebase.analytics.FirebaseAnalytics
import io.realm.Realm

class RefineApp : Application() {
    private val TAG = "refn_appp_tager"

    companion object {
        lateinit var onInterAdListener: OnInterAdListener
        val startTime = System.currentTimeMillis()
    }

    override fun onCreate() {
        super.onCreate()

        init()
        setConfigs()
        setRemoteLogs()

    }

    private fun init() {
        Realm.init(this)
        DataStoreInstance.init(this)
        FirebaseAnalytics.getInstance(this)
        MobileAds.setRequestConfiguration(
            RequestConfiguration.Builder()
                .setTestDeviceIds(listOf(getString(R.string.test_device_id)))
                .build()
        )
        MobileAds.initialize(this)
        onInterAdListener = InterAd(this)
    }

    private fun setConfigs() {
        val configViewModel = ConfigViewModel(this)

        configViewModel.setConfigDataFromDataStore()
        configViewModel.setConfigData()
    }

    private fun setRemoteLogs() {
        Utils.logRemote(hashMapOf(Pair("msg", "App open"), Pair("tag", "app")))
    }


}