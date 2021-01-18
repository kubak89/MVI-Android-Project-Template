package com.example.base.nav

sealed class Direction {
    data class toHomeScreen(val text: String): Direction()
}
