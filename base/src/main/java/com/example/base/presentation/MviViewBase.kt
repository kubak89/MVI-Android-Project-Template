package com.example.base.presentation

abstract class MviViewBase<VIEW_STATE, VIEW_INTENT> : MviView<VIEW_STATE, VIEW_INTENT> {
    private val listeners = mutableSetOf<MviView.Listener<VIEW_INTENT>>()

    override fun onViewIntent(viewIntent: VIEW_INTENT) {
        listeners.forEach {
            it.onViewIntent(viewIntent)
        }
    }

    override fun registerListener(listener: MviView.Listener<VIEW_INTENT>) {
        listeners.add(listener)
    }

    override fun unregisterListener(listener: MviView.Listener<VIEW_INTENT>) {
        listeners.remove(listener)
    }
}
