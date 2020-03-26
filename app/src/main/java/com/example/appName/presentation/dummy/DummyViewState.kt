package com.example.appName.presentation.dummy

import java.io.Serializable

data class DummyViewState(
        val mainText: String,
        val isLoggedIn: Boolean
) : Serializable {
    sealed class PartialState {
        class WelcomeState(val welcomeText: String) : PartialState()
        class LoggedState(val loggedName: String) : PartialState()
    }
}
