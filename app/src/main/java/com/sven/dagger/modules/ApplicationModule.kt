package com.sven.dagger.modules

import android.app.Application
import android.content.Context
import com.sven.BaseApplication
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class ApplicationModule(private val application: BaseApplication)  {

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

}