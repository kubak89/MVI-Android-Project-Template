package com.example.appName.presentation.features.base

import io.reactivex.Observable
import java.io.Serializable

abstract class BasePresenter<VIEW_STATE : Serializable, PARTIAL_VIEW_STATE>(
        private val initialState: VIEW_STATE
) {
    var currentViewState: VIEW_STATE? = null
        private set

    val stateObservable: Observable<VIEW_STATE> by lazy {
        subscribeToViewIntents(initialState, provideViewIntents()).doOnNext {
            currentViewState = it
        }
    }

    private fun subscribeToViewIntents(initialState: VIEW_STATE, observables: List<Observable<PARTIAL_VIEW_STATE>>) = Observable
            .merge(observables)
            .scan(initialState, this::reduceViewState)

    protected abstract fun provideViewIntents(): List<Observable<PARTIAL_VIEW_STATE>>

    protected abstract fun reduceViewState(previousState: VIEW_STATE, partialState: PARTIAL_VIEW_STATE): VIEW_STATE
}
