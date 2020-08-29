package com.sven

import android.app.Application
import android.util.Log
import com.sven.dagger.components.BaseComponent
import com.sven.dagger.components.DaggerBaseComponent
import com.sven.dagger.modules.ApplicationModule
import com.sven.util.Injector
import timber.log.Timber

class BaseApplication : Application() {
    // Dependency Injection
    private var component: BaseComponent? = null

    override fun onCreate() {
        super.onCreate()

        // Logging
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        } else {
            Timber.plant(CrashReportingTree())
        }

        // Dependency
        component = DaggerBaseComponent.builder().applicationModule(ApplicationModule(this)).build()
        component?.inject(this)
    }

    /**
     * A tree which logs important information for crash reporting.
     */
    private class CrashReportingTree : Timber.Tree() {
        override fun log(priority: Int, tag: String, message: String, t: Throwable?) {
            var msg = message
            if (priority == Log.VERBOSE || priority == Log.DEBUG) {
                return
            }

            if (BuildConfig.DEBUG) {
                if (t != null) {
                    msg = message + '\n'.toString() + Log.getStackTraceString(t) // mimic what Log.[v|d|i|w|e](TAG, message, throwable) does
                }
                Log.println(priority, tag, msg)
                return
            }

//            Crashlytics.log(priority, tag, msg)

            if (t != null) {
                when (priority) {
//                    Log.WARN, Log.ERROR -> Crashlytics.logException(t)
                }
            }
        }
    }

    override fun getSystemService(name: String): Any {
        if (Injector.matchesService(name)) {
            return component!!
        } else {
            return super.getSystemService(name)
        }
    }

    companion object {
        val INJECTOR_SERVICE = "com.whistle.bolt.InjectorService"
    }
}
