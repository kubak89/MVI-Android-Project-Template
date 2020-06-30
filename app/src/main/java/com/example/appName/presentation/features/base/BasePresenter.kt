package com.example.appName.presentation.features.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.processors.FlowableProcessor
import io.reactivex.rxjava3.processors.PublishProcessor
import io.reactivex.rxjava3.schedulers.Schedulers
import java.io.Serializable

abstract class BasePresenter<VIEW_STATE : Serializable, PARTIAL_VIEW_STATE, INTENT>(
        initialState: VIEW_STATE
) : ViewModel() {
    val stateLiveData: LiveData<VIEW_STATE> get() = mutableStateLiveData

    protected val intentProcessor: FlowableProcessor<INTENT> = PublishProcessor.create()

    private val mutableStateLiveData: MutableLiveData<VIEW_STATE> = MutableLiveData<VIEW_STATE>()

    private var disposable: Disposable? = null

    init {
        @Suppress("LeakingThis")
        disposable = subscribeToViewIntents(initialState, provideViewIntents())
    }

    override fun onCleared() {
        super.onCleared()

        disposable?.dispose()
    }

    fun acceptIntent(intent: INTENT) {
        intentProcessor.onNext(intent)
    }

    private fun subscribeToViewIntents(initialState: VIEW_STATE, flowables: Flowable<PARTIAL_VIEW_STATE>) =
            flowables
                    .scan(initialState, this::reduceViewState)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({ mutableStateLiveData.value = it }, { it.printStackTrace() })

    protected abstract fun provideViewIntents(): Flowable<PARTIAL_VIEW_STATE>

    protected abstract fun reduceViewState(previousState: VIEW_STATE, partialState: PARTIAL_VIEW_STATE): VIEW_STATE
}
