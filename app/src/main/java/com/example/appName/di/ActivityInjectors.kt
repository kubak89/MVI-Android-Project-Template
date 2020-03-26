package com.example.appName.di

import com.example.appName.presentation.features.dummy.DummyActivity
import com.example.appName.presentation.features.dummy.DummyModule
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityInjectors {

    @ContributesAndroidInjector(modules = [DummyModule::class])
    abstract fun bindDummyActivity(): DummyActivity
}
