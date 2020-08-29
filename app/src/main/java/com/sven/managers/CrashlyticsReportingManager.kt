package com.sven.managers

import android.util.Log
import com.google.firebase.crashlytics.FirebaseCrashlytics

class CrashlyticsReportingManager(private val crashlytics: FirebaseCrashlytics) : CrashReportingManager {
    override fun enableCrashReporting(enabled: Boolean) {
        crashlytics.setCrashlyticsCollectionEnabled(enabled)
    }

    override fun log(priority: Int, tag: String?, message: String?) {
        val msg = message ?: "null message"
        if (priority == 0) {
            crashlytics.log(msg)
        } else {
            val tagString = if (tag.isNullOrBlank()) "" else "$tag -"
            crashlytics.log("${priorityString(priority)}/ $tagString $msg")
        }
    }

    override fun recordException(throwable: Throwable?) = crashlytics.recordException(throwable ?: Throwable("NULL EXCEPTION"))

    override fun setUserId(userId: String) = crashlytics.setUserId(userId)

    override fun clearUserId() = crashlytics.setUserId("")

    // Crashlytics expects non-null values, so just translate to some default values. Figured MIN_VALUE was a better choice than
    // 0 or -1 because those values might actually be representative of something we're tracking and would also be easier to
    // identify in order to isolate problematic calls
    override fun setCustomKey(key: String, value: Boolean?) = crashlytics.setCustomKey(key, value ?: false)

    override fun setCustomKey(key: String, value: Double?) = crashlytics.setCustomKey(key, value ?: Int.MIN_VALUE.toDouble())

    override fun setCustomKey(key: String, value: Float?) = crashlytics.setCustomKey(key, value ?: Int.MIN_VALUE.toFloat())

    override fun setCustomKey(key: String, value: Int?) = crashlytics.setCustomKey(key, value ?: Int.MIN_VALUE)

    override fun setCustomKey(key: String, value: Long?) = crashlytics.setCustomKey(key, value ?: Int.MIN_VALUE.toLong())

    override fun setCustomKey(key: String, value: String?) = crashlytics.setCustomKey(key, value ?: "null")

    private fun priorityString(priority: Int): String {
        return when (priority) {
            Log.VERBOSE -> "V"
            Log.DEBUG -> "D"
            Log.INFO -> "I"
            Log.WARN -> "W"
            Log.ERROR -> "E"
            else -> ""
        }
    }
}