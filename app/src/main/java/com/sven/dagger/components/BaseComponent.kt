package com.sven.dagger.components

import com.sven.BaseApplication
import com.sven.dagger.modules.ApplicationModule
import com.sven.ui.main.MainActivity
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [(ApplicationModule::class)])
interface BaseComponent {
    fun inject(application: BaseApplication)

    fun inject(activity: MainActivity)
}