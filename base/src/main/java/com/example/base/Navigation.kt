package com.example.base

interface Navigation {
    sealed class Direction {
        object toDemo: Direction()
    }

    fun navigate(direction: Direction)
}
