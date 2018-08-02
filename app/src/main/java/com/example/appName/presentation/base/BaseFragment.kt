package com.example.appName.presentation.base

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import io.reactivex.android.schedulers.AndroidSchedulers
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

    override fun onResume() {
        super.onResume()

        subscribeToViewState()
    }

    override fun onPause() {
        super.onPause()

        presenter.dispose()
        disposable?.dispose()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

        outState.putSerializable(KEY_SAVED_FRAGMENT_STATE, presenter.getCurrentViewState())
    }


    protected fun setActivityTitle(title: String) {
        (activity as? AppCompatActivity)?.supportActionBar?.title = title
    }

    protected fun setDisplayHomeAsUpEnabled(homeEnabled: Boolean) {
        (activity as? AppCompatActivity)?.supportActionBar?.setDisplayHomeAsUpEnabled(homeEnabled)
    }


    abstract fun render(viewState: VIEW_STATE)
}