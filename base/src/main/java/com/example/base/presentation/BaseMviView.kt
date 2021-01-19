package com.example.base.presentation

import android.view.View

abstract class BaseMviView<VIEW_STATE, VIEW_INTENT> {
    interface Listener<VIEW_INTENT> {
        fun onViewIntent(viewIntent: VIEW_INTENT)
    }
    private var listener: Listener<VIEW_INTENT>? = null

    fun onViewIntent(viewIntent: VIEW_INTENT) {
        listener?.onViewIntent(viewIntent)
    }

    fun registerListener(listener: Listener<VIEW_INTENT>) {
        this.listener = listener
    }

    fun unregisterListener(listener: Listener<VIEW_INTENT>) {
        this.listener = listener
    }

    abstract val rootView: View
    abstract fun render(viewState: VIEW_STATE)
}
