package com.example.appName.presentation.main

import com.example.appName.presentation.base.BasePresenter
import javax.inject.Inject

class MainPresenter @Inject constructor(private val view: MainView,
                                        private val initialState: MainViewState) : BasePresenter<MainViewState, MainPartialState>() {
    override fun connectToView() {
        TODO("Create intent observables")

        subscribeToViewIntents(initialState)
    }

    override fun reduceViewState(previousState: MainViewState, partialState: MainPartialState) =
            TODO()
}