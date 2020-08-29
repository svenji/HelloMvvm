package com.sven.dagger.components

import com.sven.BaseApplication
import com.sven.dagger.modules.ActivitiesModule
import com.sven.dagger.modules.ApplicationModule
import com.sven.dagger.modules.FragmentsModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(modules = [
    AndroidSupportInjectionModule::class,
    ApplicationModule::class,
    ActivitiesModule::class,
    FragmentsModule::class
])
interface BaseComponent : AndroidInjector<BaseApplication> {
    @Component.Factory
    interface Factory {
        fun create(@BindsInstance application: BaseApplication, applicationModule: ApplicationModule): BaseComponent
    }
}