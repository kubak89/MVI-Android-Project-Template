package com.example.appName.presentation.features.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.LiveDataReactiveStreams
import androidx.lifecycle.ViewModel
import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.processors.FlowableProcessor
import io.reactivex.rxjava3.processors.PublishProcessor
import java.io.Serializable

abstract class BasePresenter<VIEW_STATE : Serializable, PARTIAL_VIEW_STATE, INTENT>(
        initialState: VIEW_STATE
) : ViewModel() {
    val stateObservable: LiveData<VIEW_STATE> by lazy {
        LiveDataReactiveStreams.fromPublisher(subscribeToViewIntents(initialState, provideViewIntents()))
    }

    protected val intentProcessor: FlowableProcessor<INTENT> = PublishProcessor.create()

    fun acceptIntent(intent: INTENT) {
        intentProcessor.onNext(intent)
    }

    private fun subscribeToViewIntents(initialState: VIEW_STATE, observables: Flowable<PARTIAL_VIEW_STATE>) = observables
            .scan(initialState, this::reduceViewState)

    protected abstract fun provideViewIntents(): Flowable<PARTIAL_VIEW_STATE>

    protected abstract fun reduceViewState(previousState: VIEW_STATE, partialState: PARTIAL_VIEW_STATE): VIEW_STATE
}
