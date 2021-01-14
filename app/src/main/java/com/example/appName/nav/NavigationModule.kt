package com.example.appName.nav

import android.content.Context
import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.example.appName.MainActivity
import com.example.appName.R
import com.example.base.Navigation
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.FragmentComponent
import dagger.hilt.android.qualifiers.ActivityContext

@Module
@InstallIn(FragmentComponent::class)
class NavigationModule {
    @Provides
    fun provideNavController(@ActivityContext context: Context): NavController {
        if (context is MainActivity) {
            return context.findNavController(R.id.main_frame)
        } else {
            throw IllegalStateException("ActivityContext is not MainActivity")
        }
    }

    @Provides
    fun provideNavigation(impl: NavigationImpl): Navigation = impl
}
