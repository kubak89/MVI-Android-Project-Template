package com.example.appName.presentation.base

import io.reactivex.Observable
import io.reactivex.disposables.Disposable
import io.reactivex.subjects.BehaviorSubject
import java.io.Serializable

abstract class BasePresenter<VIEW_STATE : Serializable, PARTIAL_VIEW_STATE> : Disposable {
    private val stateSubject: BehaviorSubject<VIEW_STATE> = BehaviorSubject.create()
    private var disposable: Disposable? = null

    fun getStateObservable(): Observable<VIEW_STATE> {
        connectToView()
        return stateSubject
    }

    fun getCurrentViewState(): VIEW_STATE? = stateSubject.value

    override fun dispose() {
        disposable?.dispose()
    }

    override fun isDisposed(): Boolean = disposable?.isDisposed ?: true

    protected fun subscribeToViewIntents(initialState: VIEW_STATE, vararg observables: Observable<PARTIAL_VIEW_STATE>) {
        val intentsDisposable = Observable
                .merge(observables.asList())
                .scan(initialState, this::reduceViewState)
                .subscribe(stateSubject::onNext)

        disposable = intentsDisposable
    }

    protected abstract fun reduceViewState(previousState: VIEW_STATE, partialState: PARTIAL_VIEW_STATE): VIEW_STATE

    protected abstract fun connectToView()
}