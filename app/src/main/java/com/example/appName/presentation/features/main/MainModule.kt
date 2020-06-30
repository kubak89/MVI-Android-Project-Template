package com.example.appName.presentation.features.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.appName.data.repository.exampleuser.ExampleUserRepository
import dagger.Module
import dagger.Provides
import javax.inject.Inject

@Module
class MainModule {

    @Provides
    fun provideInitialMainViewState(): MainViewState = MainViewState()

    @Provides
    fun provideMainPresenter(mainActivity: MainActivity, mainPresenterFactory: MainPresenterFactory) =
            ViewModelProvider(mainActivity, mainPresenterFactory).get(MainPresenter::class.java)
}

class MainPresenterFactory @Inject constructor(
        private val initialState: MainViewState,
        private val exampleUserRepository: ExampleUserRepository) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T =
            MainPresenter(initialState, exampleUserRepository) as T
}
