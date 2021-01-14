package com.example.user.di

import com.example.user.presentation.MainViewState
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent

@Module
@InstallIn(ActivityComponent::class)
class MainModule {

    @Provides
    fun provideInitialMainViewState(): MainViewState = MainViewState()
}
