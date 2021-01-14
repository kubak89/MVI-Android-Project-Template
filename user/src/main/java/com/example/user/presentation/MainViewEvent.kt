package com.example.user.presentation

import com.example.base.nav.Navigation

sealed class MainViewEvent {
    object LoginFailed: MainViewEvent()
    data class Navigate(val destination: Navigation.Direction): MainViewEvent()
}
