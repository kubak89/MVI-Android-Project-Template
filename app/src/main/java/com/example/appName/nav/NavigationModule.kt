package com.example.appName.nav

import android.content.Context
import android.util.Log
import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.example.appName.MainActivity
import com.example.appName.R
import com.example.base.nav.Navigation
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.components.FragmentComponent
import dagger.hilt.android.qualifiers.ActivityContext
import javax.inject.Provider

@Module
@InstallIn(ActivityComponent::class)
class NavigationModule {
    @Provides
    fun provideNavigation(impl: NavigationImpl): Navigation = impl
}
