package com.example.appName.presentation.features.main

import com.example.appName.presentation.features.base.BaseActivity
import dagger.Module
import dagger.Provides

@Module
class MainModule {

    @Provides
    fun provideMainView(activity: MainActivity): MainView = activity

    @Provides
    fun provideInitialMainViewState(activity: MainActivity): MainViewState = activity.savedInstanceState?.getSerializable(
            BaseActivity.KEY_SAVED_ACTIVITY_VIEW_STATE) as? MainViewState
            ?: MainViewState(mainText = "Welcome Stranger!", isLoggedIn = false)
}
