package com.example.appName.presentation.features.base

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.processors.FlowableProcessor
import io.reactivex.rxjava3.processors.PublishProcessor
import io.reactivex.rxjava3.schedulers.Schedulers
import java.io.Serializable

abstract class BasePresenter<VIEW_STATE : Serializable, PARTIAL_VIEW_STATE, INTENT>(
        initialState: VIEW_STATE
) : ViewModel() {
    val stateLiveData: MutableLiveData<VIEW_STATE> = MutableLiveData<VIEW_STATE>()

    protected val intentProcessor: FlowableProcessor<INTENT> = PublishProcessor.create()

    init {
        @Suppress("LeakingThis")
        subscribeToViewIntents(initialState, provideViewIntents())
    }

    fun acceptIntent(intent: INTENT) {
        intentProcessor.onNext(intent)
    }

    private fun subscribeToViewIntents(initialState: VIEW_STATE, flowables: Flowable<PARTIAL_VIEW_STATE>) =
            flowables
                    .scan(initialState, this::reduceViewState)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({ stateLiveData.value = it }, { it.printStackTrace() })

    protected abstract fun provideViewIntents(): Flowable<PARTIAL_VIEW_STATE>

    protected abstract fun reduceViewState(previousState: VIEW_STATE, partialState: PARTIAL_VIEW_STATE): VIEW_STATE
}
