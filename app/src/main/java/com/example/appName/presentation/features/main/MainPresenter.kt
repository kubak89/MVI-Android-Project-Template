package com.example.appName.presentation.features.main

import com.example.appName.data.repository.exampleuser.ExampleUserRepository
import com.example.appName.presentation.features.base.BasePresenter
import com.example.appName.presentation.features.main.MainConstants.LOGGED_OUT_NAME
import io.reactivex.Observable
import javax.inject.Inject

class MainPresenter @Inject constructor(
        private val view: MainView,
        initialState: MainViewState,
        private val exampleUserRepository: ExampleUserRepository
) : BasePresenter<MainViewState, MainViewState.PartialState>(initialState) {

    override fun reduceViewState(previousState: MainViewState, partialState: MainViewState.PartialState): MainViewState =
            when (partialState) {
                is MainViewState.PartialState.WelcomeState ->
                    previousState.copy(mainText = partialState.welcomeText, isLoggedIn = false)
                is MainViewState.PartialState.LoggedInState ->
                    previousState.copy(mainText = partialState.loggedInText, isLoggedIn = true)

            }

    override fun provideViewIntents(): List<Observable<MainViewState.PartialState>> = listOf(
            login(),
            logout()
    )

    private fun logout(): Observable<MainViewState.PartialState> =
            view.logoutIntent.map { MainViewState.PartialState.WelcomeState(welcomeText = getGreetingsText(LOGGED_OUT_NAME)) }

    private fun login(): Observable<MainViewState.PartialState> =
            view.loginIntent.switchMap {
                exampleUserRepository.getUser().toObservable()
            }.map { user ->
                MainViewState.PartialState.LoggedInState(loggedInText = getGreetingsText(user.name))
            }

    private fun getGreetingsText(name: String) = "Welcome $name!"
}
