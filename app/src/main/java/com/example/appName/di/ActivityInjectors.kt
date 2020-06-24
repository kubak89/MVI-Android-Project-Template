package com.example.appName.di

import com.example.appName.presentation.features.main.MainActivity
import com.example.appName.presentation.features.main.MainModule
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityInjectors {

    @ContributesAndroidInjector(modules = [MainModule::class])
    abstract fun bindMainActivity(): MainActivity
}
