package com.sven.dagger.modules

import android.app.Application
import android.content.Context
import com.google.firebase.crashlytics.FirebaseCrashlytics
import com.sven.BaseApplication
import com.sven.managers.CrashReportingManager
import com.sven.managers.CrashlyticsReportingManager
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class ApplicationModule(private val application: BaseApplication) {

    //////////////////////////////////////////////////
    //////////////////// Android /////////////////////
    //////////////////////////////////////////////////
    @Provides
    @Singleton
    internal fun providesApplication(): Application {
        return this.application
    }

    @Provides
    @Singleton
    internal fun providesWhistleApplication(): BaseApplication {
        return this.application
    }

    @Provides
    @Singleton
    internal fun providesApplicationContext(): Context {
        return application.applicationContext
    }

    //////////////////////////////////////////////////
    /////////////// Crash Reporting //////////////////
    //////////////////////////////////////////////////
    @Provides
    fun providesCrashlytics(): FirebaseCrashlytics {
        return FirebaseCrashlytics.getInstance()
    }

    @Provides
    @Singleton
    fun providesCrashReportingManager(crashlytics: FirebaseCrashlytics): CrashReportingManager {
        return CrashlyticsReportingManager(crashlytics)
    }
}