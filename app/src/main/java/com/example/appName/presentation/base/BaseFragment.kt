package com.example.appName.presentation.base

import android.os.Bundle
import android.support.v4.app.Fragment
import com.example.appName.presentation.utils.ApplicationNavigator
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import java.io.Serializable
import javax.inject.Inject

const val KEY_SAVED_FRAGMENT_STATE = "dashboardState"

abstract class BaseFragment<VIEW_STATE : Serializable, PRESENTER : Presenter<VIEW_STATE, *>> : Fragment() {
    protected val disposables: CompositeDisposable = CompositeDisposable()
    protected val applicationNavigator: ApplicationNavigator by lazy {
        (activity as NavigatorActivity).applicationNavigator
    }

    @Inject
    lateinit var presenter: PRESENTER

    protected fun subscribeToViewState() {
        disposables.add(presenter.getStateObservable().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe(this::render))
    }

    override fun onDestroy() {
        super.onDestroy()

        disposables.dispose()
    }

    abstract fun render(viewState: VIEW_STATE)


    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

        outState.putSerializable(KEY_SAVED_FRAGMENT_STATE, presenter.getCurrentViewState())
    }
}