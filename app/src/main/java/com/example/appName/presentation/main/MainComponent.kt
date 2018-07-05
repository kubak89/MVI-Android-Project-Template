package com.example.appName.presentation.main

import com.example.appName.presentation.di.ActivityModule
import com.example.appName.presentation.di.ActivityScope
import com.example.appName.presentation.di.ApplicationComponent
import dagger.Component

@ActivityScope
@Component(modules = [MainModule::class, ActivityModule::class],
        dependencies = [ApplicationComponent::class])
interface MainComponent {
    fun inject(activity: MainActivity)
}
