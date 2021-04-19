package com.app.refine.utils

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

}