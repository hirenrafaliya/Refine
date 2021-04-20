package com.app.refine.listener

import android.app.Activity
import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.google.android.gms.ads.FullScreenContentCallback
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.rewarded.RewardedAd

interface OnInterAdListener {
    fun reInitInterstitialAd()
    fun showInterstitialAd(activity: Activity)
    fun isInterstitialAdLoaded(): Boolean
    fun isInterstitialAdFailedToLoad():Boolean
    fun setAdListener(callback: FullScreenContentCallback?)
    fun getInterAd(): InterstitialAd?
    fun setInterAd(ad: InterstitialAd)
    fun getLiveInterAd(): MutableLiveData<InterstitialAd?>
}