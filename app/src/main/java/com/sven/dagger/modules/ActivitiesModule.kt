package com.sven.dagger.modules

import com.sven.ui.main.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivitiesModule {
    //////////////////////////////////////////////////
    ////////////////////// Main //////////////////////
    //////////////////////////////////////////////////
    @ContributesAndroidInjector
    abstract fun contributeMainActivity(): MainActivity
}