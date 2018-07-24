package com.example.appName.presentation.base

import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.subjects.BehaviorSubject
import java.io.Serializable

abstract class BasePresenter<VIEW_STATE : Serializable, PARTIAL_VIEW_STATE> : Disposable {
    private val stateSubject: BehaviorSubject<VIEW_STATE> = BehaviorSubject.create()
    private val disposables: CompositeDisposable = CompositeDisposable()

    fun getStateObservable() = stateSubject as Observable<VIEW_STATE>
    fun getCurrentViewState(): VIEW_STATE? = stateSubject.value

    override fun dispose() = disposables.dispose()

    override fun isDisposed(): Boolean = disposables.isDisposed

    protected fun subscribeToViewIntents(initialState: VIEW_STATE, vararg observables: Observable<PARTIAL_VIEW_STATE>) {
        val intentsDisposable = Observable
                .merge(observables.asList())
                .scan(initialState, this::reduceViewState)
                .subscribe(stateSubject::onNext)

        disposables.add(intentsDisposable)
    }

    protected abstract fun reduceViewState(previousState: VIEW_STATE, partialState: PARTIAL_VIEW_STATE): VIEW_STATE
}