package com.example.appName.presentation.di

import android.support.v7.app.AppCompatActivity
import com.example.appName.presentation.utils.ApplicationNavigator
import dagger.Module
import dagger.Provides

@Module
class ActivityModule(private val baseActivity: AppCompatActivity) {
    @Provides
    fun provideApplicationNavigator() =
            ApplicationNavigator(baseActivity)
}