package com.example.appName.presentation.features.main

import java.io.Serializable

data class MainViewState(
        val mainText: String,
        val isLoggedIn: Boolean
) : Serializable {
    sealed class PartialState {
        class WelcomeState(val welcomeText: String) : PartialState()
        class LoggedState(val loggedText: String) : PartialState()
    }
}
