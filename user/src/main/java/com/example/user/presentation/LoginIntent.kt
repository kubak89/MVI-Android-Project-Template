package com.example.user.presentation

sealed class LoginIntent {
    object Login : LoginIntent()
    object Logout : LoginIntent()
}
