package com.example.user.presentation

sealed class MainViewEvent {
    object LoginFailed: MainViewEvent()
}
