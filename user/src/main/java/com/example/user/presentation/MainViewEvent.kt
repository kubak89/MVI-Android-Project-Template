package com.example.user.presentation

import com.example.base.Navigation

sealed class MainViewEvent {
    object LoginFailed: MainViewEvent()
    data class Navigate(val destination: Navigation.Direction): MainViewEvent()
}
