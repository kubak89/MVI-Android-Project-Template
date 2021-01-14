package com.example.base.nav

interface Navigation {
    sealed class Direction {
        data class toHomeScreen(val text: String): Direction()
    }

    fun navigate(direction: Direction)
}
