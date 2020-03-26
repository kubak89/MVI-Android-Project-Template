package com.example.appName.presentation.dummy

import com.example.appName.presentation.base.BaseActivity
import dagger.Module
import dagger.Provides

@Module
class DummyModule {

    @Provides
    fun provideMainView(activity: DummyActivity): DummyView = activity

    @Provides
    fun provideInitialMainViewState(activity: DummyActivity): DummyViewState = activity.savedInstanceState?.getSerializable(
            BaseActivity.KEY_SAVED_ACTIVITY_VIEW_STATE) as? DummyViewState
            ?: DummyViewState(mainText = "Welcome Stranger!", isLoggedIn = false)
}
