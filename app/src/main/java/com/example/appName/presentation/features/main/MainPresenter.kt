package com.example.appName.presentation.features.main

import com.example.appName.data.repository.exampleuser.ExampleUserRepository
import com.example.appName.presentation.features.base.BasePresenter
import com.example.appName.presentation.features.main.MainViewState.PartialState.LoggedInState
import com.example.appName.presentation.features.main.MainViewState.PartialState.WelcomeState
import io.reactivex.rxjava3.core.Flowable
import javax.inject.Inject

class MainPresenter @Inject constructor(
        initialState: MainViewState,
        private val exampleUserRepository: ExampleUserRepository
) : BasePresenter<MainViewState, MainViewState.PartialState, MainIntent>(initialState) {

    override fun reduceViewState(previousState: MainViewState, partialState: MainViewState.PartialState): MainViewState =
            when (partialState) {
                is WelcomeState ->
                    previousState.copy(name = partialState.loggedOutName, isLoggedIn = false)
                is LoggedInState ->
                    previousState.copy(name = partialState.loggedInName, isLoggedIn = true)
            }

    override fun provideViewIntents(): Flowable<MainViewState.PartialState> =
            intentProcessor.flatMap {
                when (it) {
                    is MainIntent.Login -> login()
                    MainIntent.Logout -> logout()
                }
            }

    private fun logout(): Flowable<MainViewState.PartialState> =
            Flowable.just(WelcomeState(loggedOutName = MainConstants.LOGGED_OUT_NAME))

    private fun login() =
            exampleUserRepository
                    .getUser()
                    .map { LoggedInState(it.name) }
                    .toFlowable()
}
