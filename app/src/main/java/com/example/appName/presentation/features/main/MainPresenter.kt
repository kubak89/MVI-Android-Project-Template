package com.example.appName.presentation.features.main

import com.example.appName.data.repository.exampleuser.ExampleUserRepository
import com.example.appName.presentation.features.base.BasePresenter
import io.reactivex.Observable
import javax.inject.Inject

class MainPresenter @Inject constructor(
        private val view: MainView,
        initialState: MainViewState,
        private val exampleUserRepository: ExampleUserRepository
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
            view.logoutIntent.map { MainViewState.PartialState.WelcomeState(welcomeText = getGreetingsText(WELCOME_NAME)) }

    private fun onLoginIntent(): Observable<MainViewState.PartialState> =
            view.loginIntent.switchMap {
                exampleUserRepository.getUser().toObservable()
            }.map { user ->
                MainViewState.PartialState.LoggedState(loggedText = getGreetingsText(user.name))
            }

    private fun getGreetingsText(name: String) = "Welcome $name!"

    companion object {
        const val WELCOME_NAME = "Stranger"
    }
}
