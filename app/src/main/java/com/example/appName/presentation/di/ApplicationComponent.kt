package com.example.appName.presentation.di

import com.example.appName.data.di.DataModule
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [ApplicationModule::class, DataModule::class])
interface ApplicationComponent