package com.example.appName.presentation.main

import com.example.appName.presentation.base.KEY_SAVED_ACTIVITY_VIEW_STATE
import dagger.Module
import dagger.Provides

@Module
class MainModule(private val activity: MainActivity) {

    @Provides
    fun provideLoginView(): MainView = activity

    @Provides
    fun provideSavedViewState(): MainViewState =
            activity.intent.getSerializableExtra(
                    KEY_SAVED_ACTIVITY_VIEW_STATE) as? MainViewState
                    ?: MainViewState()
}
