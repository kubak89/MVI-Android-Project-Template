package com.example.appName.presentation.base

import android.os.Bundle
import android.support.v4.app.Fragment
import com.example.appName.presentation.utils.ApplicationNavigator
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import java.io.Serializable
import javax.inject.Inject

const val KEY_SAVED_FRAGMENT_STATE = "dashboardState"

abstract class BaseFragment<VIEW_STATE : Serializable, PRESENTER : BasePresenter<VIEW_STATE, *>> : Fragment() {
    private var disposable: Disposable? = null

    @Inject
    lateinit var presenter: PRESENTER

    protected fun subscribeToViewState() {
        disposable = presenter.getStateObservable().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe(this::render)
    }

    override fun onDestroy() {
        super.onDestroy()

        disposable?.dispose()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

        outState.putSerializable(KEY_SAVED_FRAGMENT_STATE, presenter.getCurrentViewState())
    }


    abstract fun render(viewState: VIEW_STATE)
}