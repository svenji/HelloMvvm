package com.sven

import android.util.Log
import com.sven.dagger.components.DaggerBaseComponent
import com.sven.dagger.modules.ApplicationModule
import com.sven.managers.CrashReportingManager
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication
import timber.log.Timber
import javax.inject.Inject

class BaseApplication : DaggerApplication() {
    @Inject lateinit var crashReportingManager: CrashReportingManager

    override fun onCreate() {
        super.onCreate()

        // Logging
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        } else {
            Timber.plant(CrashReportingTree(crashReportingManager))
        }
    }

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        return DaggerBaseComponent.factory().create(this, ApplicationModule(this))
    }

    /**
     * A tree which logs important information for crash reporting.
     */
    private class CrashReportingTree(private val crashReportingManager: CrashReportingManager) : Timber.Tree() {
        override fun log(priority: Int, tag: String, message: String, t: Throwable?) {
            var msg = message
            if (priority == Log.VERBOSE || priority == Log.DEBUG) {
                return
            }

            if (BuildConfig.DEBUG) {
                if (t != null) {
                    // mimic what Log.[v|d|i|w|e](TAG, message, throwable) does
                    msg = "message\n${Log.getStackTraceString(t)}"
                }
                Log.println(priority, tag, msg)
                return
            }

            crashReportingManager.log(priority, tag, msg)

            if (t != null) {
                when (priority) {
                    Log.WARN, Log.ERROR -> crashReportingManager.recordException(t)
                }
            }
        }
    }
}
