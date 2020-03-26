package com.example.appName.presentation.dummy

import com.example.appName.presentation.base.BasePresenter
import io.reactivex.Observable
import javax.inject.Inject

class DummyPresenter @Inject constructor(
        private val view: DummyView,
        initialState: DummyViewState
) : BasePresenter<DummyViewState, DummyViewState.PartialState>(initialState) {

    override fun reduceViewState(previousState: DummyViewState, partialState: DummyViewState.PartialState): DummyViewState {
        return when (partialState) {
            is DummyViewState.PartialState.WelcomeState ->
                previousState.copy(mainText = partialState.welcomeText, isLoggedIn = false)
            is DummyViewState.PartialState.LoggedState ->
                previousState.copy(mainText = partialState.loggedName, isLoggedIn = true)
        }
    }

    override fun provideViewIntents(): List<Observable<DummyViewState.PartialState>> = listOf(
            onLoginIntent(),
            onLogoutIntent()
    )

    private fun onLogoutIntent(): Observable<DummyViewState.PartialState> =
            view.logoutIntent.map { DummyViewState.PartialState.WelcomeState(welcomeText = "Welcome Stranger!") }

    private fun onLoginIntent(): Observable<DummyViewState.PartialState> =
            view.loginIntent.map { DummyViewState.PartialState.LoggedState(loggedName = "Welcome back User!") }
}
