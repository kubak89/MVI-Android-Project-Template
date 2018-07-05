package com.example.appName.presentation.main

import com.example.appName.presentation.base.Presenter
import javax.inject.Inject

class MainPresenter @Inject constructor(view: MainView,
                                        initialState: MainViewState) : Presenter<MainViewState, MainPartialState>() {

    init {
        TODO("Subscribe to view intents")

        subscribeToViewIntents(initialState)
    }

    override fun reduceViewState(previousState: MainViewState, partialState: MainPartialState) =
            TODO()
}