package com.example.user.presentation

sealed class MainIntent {
    object Login : MainIntent()
    object Logout : MainIntent()
}
