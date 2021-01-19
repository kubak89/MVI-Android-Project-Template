package com.example.base.presentation

import android.view.View

interface MviView<VIEW_STATE, VIEW_INTENT> {
    val rootView: View

    fun render(viewState: VIEW_STATE)

    interface Listener<VIEW_INTENT> {
        fun onViewIntent(viewIntent: VIEW_INTENT)
    }

    fun registerListener(listener: Listener<VIEW_INTENT>)
    fun unregisterListener(listener: Listener<VIEW_INTENT>)

    fun onViewIntent(viewIntent: VIEW_INTENT)
}

