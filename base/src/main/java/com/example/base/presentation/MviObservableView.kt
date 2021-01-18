package com.example.base.presentation

interface MviObservableView<VIEW_STATE, VIEW_INTENT> : MviView<VIEW_STATE> {
    interface Listener<VIEW_INTENT> {
        fun onViewIntent(viewIntent: VIEW_INTENT)
    }

    fun registerListener(listener: Listener<VIEW_INTENT>)
    fun unregisterListener(listener: Listener<VIEW_INTENT>)

    fun onViewIntent(viewIntent: VIEW_INTENT)
}

