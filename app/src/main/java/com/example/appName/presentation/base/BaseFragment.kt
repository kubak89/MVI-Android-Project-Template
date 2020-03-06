package com.example.appName.presentation.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import dagger.android.support.AndroidSupportInjection
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import java.io.Serializable
import javax.inject.Inject

abstract class BaseFragment<VIEW_STATE : Serializable, PRESENTER : BasePresenter<VIEW_STATE, *>>(
        @LayoutRes val layoutId: Int
) : Fragment(), HasAndroidInjector {

    @Inject
    lateinit var presenter: PRESENTER

    @Inject
    lateinit var childFragmentInjector: DispatchingAndroidInjector<Any>

    var savedInstanceState: Bundle? = null

    private var disposable: Disposable? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
        inflater.inflate(layoutId, null, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        this.savedInstanceState = savedInstanceState
        AndroidSupportInjection.inject(this)
        super.onViewCreated(view, savedInstanceState)
        subscribeToViewState()
        bind()
    }

    override fun onDestroy() {
        super.onDestroy()

        disposable?.dispose()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

        outState.putSerializable(KEY_SAVED_FRAGMENT_VIEW_STATE, presenter.currentViewState)
    }

    override fun androidInjector(): AndroidInjector<Any> = childFragmentInjector

    private fun subscribeToViewState() {
        disposable = presenter.stateObservable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe(this::render)
    }

    open fun bind() {}

    abstract fun render(viewState: VIEW_STATE)

    companion object {
        const val KEY_SAVED_FRAGMENT_VIEW_STATE = "viewState"
    }
}
