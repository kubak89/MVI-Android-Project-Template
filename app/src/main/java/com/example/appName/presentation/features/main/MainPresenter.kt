package com.example.appName.presentation.features.main

import androidx.hilt.lifecycle.ViewModelInject
import com.example.appName.data.repository.exampleuser.ExampleUserRepository
import com.example.base.BasePresenter
import com.example.appName.presentation.features.main.MainViewState.PartialState.LoggedInState
import com.example.appName.presentation.features.main.MainViewState.PartialState.WelcomeState
import io.reactivex.rxjava3.core.Flowable

class MainPresenter @ViewModelInject constructor(
        initialState: MainViewState,
        private val exampleUserRepository: ExampleUserRepository
) : BasePresenter<MainViewState, MainViewState.PartialState, MainIntent, MainViewEvent>(initialState) {

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

    private fun logout(): Flowable<MainViewState.PartialState> {
        return Flowable.just(WelcomeState(loggedOutName = MainConstants.LOGGED_OUT_NAME))
    }

    private var loginCalls = 0
    private fun login(): Flowable<out MainViewState.PartialState> {
        loginCalls += 1
        if (loginCalls == 2) {
            publishEvent(MainViewEvent.LoginFailed)
            return Flowable.empty()
        }

        return exampleUserRepository
                .getUser()
                .map { LoggedInState(it.name) }
                .toFlowable()
    }

}
