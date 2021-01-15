package com.example.user.presentation

import com.example.base.nav.Navigation

sealed class LoginViewEvent {
    object LoginFailed: LoginViewEvent()
    data class Navigate(val destination: Navigation.Direction): LoginViewEvent()
}
