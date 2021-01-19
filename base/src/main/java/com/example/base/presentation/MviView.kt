package com.example.base.presentation

interface MviView<VIEW_STATE> {
    fun render(viewState: VIEW_STATE)
}
