package com.example.base.presentation

abstract class MviObservableViewBase<VIEW_STATE, VIEW_INTENT> : MviObservableView<VIEW_STATE, VIEW_INTENT> {
    private val listeners = mutableSetOf<MviObservableView.Listener<VIEW_INTENT>>()

    override fun onViewIntent(viewIntent: VIEW_INTENT) {
        listeners.forEach {
            it.onViewIntent(viewIntent)
        }
    }

    override fun registerListener(listener: MviObservableView.Listener<VIEW_INTENT>) {
        listeners.add(listener)
    }

    override fun unregisterListener(listener: MviObservableView.Listener<VIEW_INTENT>) {
        listeners.remove(listener)
    }
}
