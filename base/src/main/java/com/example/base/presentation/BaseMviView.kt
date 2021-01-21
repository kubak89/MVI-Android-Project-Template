package com.example.base.presentation

import android.view.View

abstract class BaseMviView<VIEW_STATE, VIEW_INTENT> {
    abstract val rootView: View
    abstract val acceptIntent: (VIEW_INTENT) -> Unit
    abstract fun render(viewState: VIEW_STATE)
}
