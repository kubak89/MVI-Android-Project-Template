package com.example.appName.data.di

import com.example.appName.presentation.dummy.DummyActivity
import com.example.appName.presentation.dummy.DummyModule
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityInjectors {

    @ContributesAndroidInjector(modules = [DummyModule::class])
    abstract fun bindDummyActivity(): DummyActivity
}
