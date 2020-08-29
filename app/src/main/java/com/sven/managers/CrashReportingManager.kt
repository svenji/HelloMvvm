package com.sven.managers

interface CrashReportingManager {
    fun enableCrashReporting(enabled: Boolean)

    fun log(priority: Int = 0, tag: String? = "", message: String?)
    fun recordException(throwable: Throwable?)

    fun setUserId(userId: String)
    fun clearUserId()

    fun setCustomKey(key: String, value: Boolean?)
    fun setCustomKey(key: String, value: Double?)
    fun setCustomKey(key: String, value: Float?)
    fun setCustomKey(key: String, value: Int?)
    fun setCustomKey(key: String, value: Long?)
    fun setCustomKey(key: String, value: String?)
}