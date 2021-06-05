package com.app.refine.utils

import com.app.refine.BuildConfig
import com.google.firebase.firestore.FirebaseFirestore
import java.text.SimpleDateFormat
import java.util.*

object Utils {

    fun getCurrentDate(): String {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH)
        dateFormat.timeZone = TimeZone.getTimeZone("UTC")
        return dateFormat.format(Date())
    }

    fun getCurrentDate(format: String): String {
        val dateFormat = SimpleDateFormat(format, Locale.ENGLISH)
        dateFormat.timeZone = TimeZone.getTimeZone("UTC")
        return dateFormat.format(Date())
    }

    fun logRemote(map: HashMap<String, Any>) {
        val userId = MongoUtils.getApp().currentUser()?.deviceId
        val date = getCurrentDate()

        map["userId"] = userId ?: "null"
        map["date"] = date
        map["appVersion"] = BuildConfig.VERSION_NAME

        if (!BuildConfig.DEBUG)
            FirebaseFirestore
                .getInstance()
                .collection("logs")
                .add(map)

    }

}