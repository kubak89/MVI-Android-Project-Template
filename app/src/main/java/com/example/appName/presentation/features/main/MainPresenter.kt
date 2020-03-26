package com.example.appName.presentation.features.main

import com.example.appName.data.repository.dummyuser.ExampleUserRepository
import com.example.appName.presentation.features.base.BasePresenter
import io.reactivex.Observable
import javax.inject.Inject

class MainPresenter @Inject constructor(
        private val view: MainView,
        initialState: MainViewState,
        private val dummyUserRepository: ExampleUserRepository
) : BasePresenter<MainViewState, MainViewState.PartialState>(initialState) {

    override fun reduceViewState(previousState: MainViewState, partialState: MainViewState.PartialState): MainViewState {
        return when (partialState) {
            is MainViewState.PartialState.WelcomeState ->
                previousState.copy(mainText = partialState.welcomeText, isLoggedIn = false)
            is MainViewState.PartialState.LoggedState ->
                previousState.copy(mainText = partialState.loggedText, isLoggedIn = true)
        }
    }

    override fun provideViewIntents(): List<Observable<MainViewState.PartialState>> = listOf(
            onLoginIntent(),
            onLogoutIntent()
    )

    private fun onLogoutIntent(): Observable<MainViewState.PartialState> =
            view.logoutIntent.map { MainViewState.PartialState.WelcomeState(welcomeText = "Welcome Stranger!") }

    private fun onLoginIntent(): Observable<MainViewState.PartialState> =
            view.loginIntent.switchMap {
                dummyUserRepository.getUser().toObservable()
            }.map {
                MainViewState.PartialState.LoggedState(loggedText = "Welcome ${it.name}!")
            }
}
