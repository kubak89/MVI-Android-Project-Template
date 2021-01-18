package com.example.appName.nav

import com.example.base.nav.Navigation
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent

@Module
@InstallIn(ActivityComponent::class)
class NavigationModule {
    @Provides
    fun provideNavigation(impl: NavigationImpl): Navigation = impl
}
