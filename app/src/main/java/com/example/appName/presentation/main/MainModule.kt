package com.example.appName.presentation.main

import android.os.Bundle
import com.example.appName.presentation.base.KEY_SAVED_ACTIVITY_VIEW_STATE
import dagger.Module
import dagger.Provides

@Module
class MainModule(private val activity: MainActivity,
                 private val savedInstanceState: Bundle?) {

    @Provides
    fun provideLoginView(): MainView = activity

    @Provides
    fun provideSavedViewState(): MainViewState =
            savedInstanceState?.getSerializable(
                    KEY_SAVED_ACTIVITY_VIEW_STATE) as? MainViewState
                    ?: MainViewState()
}
