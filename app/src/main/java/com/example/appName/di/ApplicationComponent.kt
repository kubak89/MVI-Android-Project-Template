package com.example.appName.di

import android.app.Application
import com.example.appName.MyApplication
import com.example.appName.presentation.features.dummy.DummyModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.support.AndroidSupportInjectionModule

@Component(modules = [
    AndroidSupportInjectionModule::class,
    ApplicationModule::class,
    ActivityInjectors::class,
    DummyModule::class
])
interface ApplicationComponent {

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder

        fun build(): ApplicationComponent
    }

    fun inject(application: MyApplication)
}
