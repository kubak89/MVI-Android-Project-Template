package com.example.appName.presentation.features.main

import com.example.appName.presentation.features.main.MainConstants.LOGGED_OUT_NAME
import java.io.Serializable

data class MainViewState(
        val mainText: String = "Welcome $LOGGED_OUT_NAME!",
        val isLoggedIn: Boolean = false
) : Serializable {
    sealed class PartialState {
        data class WelcomeState(val welcomeText: String) : PartialState()
        data class LoggedInState(val loggedInText: String) : PartialState()
    }
}
