package com.example.base.presentation

import android.view.View

interface MviView<VIEW_STATE> {
    val rootView: View

    fun render(viewState: VIEW_STATE)
}
