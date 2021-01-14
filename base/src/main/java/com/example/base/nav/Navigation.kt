package com.example.base.nav

interface Navigation {
    sealed class Direction {
        object toDemo: Direction()
    }

    fun navigate(direction: Direction)
}
