package com.example.appName.presentation.base

import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import java.io.Serializable

abstract class BasePresenter<VIEW_STATE : Serializable, PARTIAL_VIEW_STATE>(
        private val initialState: VIEW_STATE
) {
    var currentViewState: VIEW_STATE? = null
        private set

    val stateObservable: Observable<VIEW_STATE> by lazy {
        subscribeToViewIntents(initialState, *provideViewIntents().toTypedArray()).doOnNext {
            currentViewState = it
        }
    }
    
    private fun subscribeToViewIntents(initialState: VIEW_STATE, vararg observables: Observable<PARTIAL_VIEW_STATE>) = Observable
            .merge(observables.asList())
            .scan(initialState, this::reduceViewState)

    protected abstract fun provideViewIntents(): List<Observable<PARTIAL_VIEW_STATE>>

    protected abstract fun reduceViewState(previousState: VIEW_STATE, partialState: PARTIAL_VIEW_STATE): VIEW_STATE
}
