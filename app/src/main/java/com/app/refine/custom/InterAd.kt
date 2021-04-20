package com.app.refine.custom

import android.app.Activity
import android.content.Context
import android.util.Log
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import com.app.refine.R
import com.app.refine.listener.OnInterAdListener
import com.app.refine.model.Config
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.FullScreenContentCallback
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback

class InterAd(val context: Context) : OnInterAdListener {
    private val TAG = "inad_cstm_tager"

    var interstitialAd: MutableLiveData<InterstitialAd?> = MutableLiveData<InterstitialAd?>()
    private var isLoading: Boolean = false
    private var isFailedToLoad = false

    override fun reInitInterstitialAd() {
        Log.d(TAG, "reInitInterstitialAd: ${Config.interExit.toString()}")

        if (interstitialAd.value == null && !isLoading)
            initializeInterstitialAd()
        else
            Log.d(TAG, "reInit:notNull")
    }

    override fun showInterstitialAd(activity: Activity) {
        if (interstitialAd.value != null) {
            interstitialAd.value?.show(activity)
            interstitialAd.value = null
            interstitialAd.removeObservers(activity as LifecycleOwner)
        } else
            reInitInterstitialAd()
    }

    override fun isInterstitialAdLoaded(): Boolean {
        Log.d(TAG, "isInterstitialAdLoaded: ")
        return interstitialAd.value != null
    }

    override fun isInterstitialAdFailedToLoad(): Boolean = isFailedToLoad

    override fun setAdListener(callback: FullScreenContentCallback?) {
        Log.d(TAG, "setAdListener: ")
        interstitialAd.value?.fullScreenContentCallback = callback
    }

    override fun getInterAd(): InterstitialAd? {
        return interstitialAd.value
    }

    override fun setInterAd(ad: InterstitialAd) {
        this.interstitialAd.postValue(ad)
    }

    override fun getLiveInterAd(): MutableLiveData<InterstitialAd?> {
        return interstitialAd
    }

    private fun initializeInterstitialAd() {
        Log.d(TAG, "initializeInterstitialAd: ")
        interstitialAd.value = null
        isLoading = true
        isFailedToLoad = false
        InterstitialAd.load(
            context,
            context.getString(R.string.interstitial_ad),
            AdRequest.Builder().build(),
            object : InterstitialAdLoadCallback() {
                override fun onAdLoaded(ad: InterstitialAd) {
                    super.onAdLoaded(ad)
                    interstitialAd.postValue(ad)
                    isLoading = false
                    Log.d(TAG, "onAdLoaded: ")
                }

                override fun onAdFailedToLoad(p0: LoadAdError) {
                    super.onAdFailedToLoad(p0)
                    interstitialAd.postValue(null)
                    isLoading = false
                    isFailedToLoad = true
                    Log.d(TAG, "onAdFailedToLoad: ")
                }
            })
    }

}