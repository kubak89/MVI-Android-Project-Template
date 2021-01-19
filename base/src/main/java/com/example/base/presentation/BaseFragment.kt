package com.example.base.presentation

import androidx.fragment.app.Fragment
import com.example.base.nav.Navigation
import com.example.base.utils.SchedulersFactory
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.kotlin.addTo
import java.io.Serializable
import javax.inject.Inject

abstract class BaseFragment<
        VIEW_STATE : Serializable,
        VIEW_INTENT,
        VIEW_EVENT,
        PRESENTER : BasePresenter<VIEW_STATE, *, VIEW_INTENT, VIEW_EVENT>>()
    : Fragment(), BaseMviView.Listener<VIEW_INTENT>  {

    abstract val presenter: PRESENTER

    abstract val mviView: BaseMviView<VIEW_STATE, VIEW_INTENT>

    @Inject
    protected lateinit var navigation: Navigation
    protected val compositeDisposable = CompositeDisposable()

    override fun onStart() {
        super.onStart()

        presenter
                .viewEvents
                .observeOn(SchedulersFactory.main)
                .subscribe(::handle)
                .addTo(compositeDisposable)

        presenter
                .viewState
                .distinctUntilChanged()
                .observeOn(SchedulersFactory.main)
                .subscribe(::render)
                .addTo(compositeDisposable)

        mviView.registerListener(this)
    }

    override fun onStop() {
        super.onStop()
        compositeDisposable.clear()
        mviView.unregisterListener(this)
    }

    override fun onViewIntent(viewIntent: VIEW_INTENT) {
        presenter.acceptIntent(viewIntent)
    }

    open fun render(viewState: VIEW_STATE) {
        mviView.render(viewState)
    }

    abstract fun handle(viewEvent: VIEW_EVENT)
}
