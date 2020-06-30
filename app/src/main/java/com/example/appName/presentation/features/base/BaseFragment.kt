package com.example.appName.presentation.features.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import androidx.lifecycle.observe
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import dagger.android.support.AndroidSupportInjection
import io.reactivex.rxjava3.disposables.Disposable
import java.io.Serializable
import javax.inject.Inject

abstract class BaseFragment<VIEW_STATE : Serializable, PRESENTER : BasePresenter<VIEW_STATE, *, *>>(
        @LayoutRes val layoutId: Int
) : Fragment(), HasAndroidInjector {

    @Inject
    lateinit var presenter: PRESENTER

    @Inject
    lateinit var childFragmentInjector: DispatchingAndroidInjector<Any>

    private var disposable: Disposable? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
            inflater.inflate(layoutId, null, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        AndroidSupportInjection.inject(this)
        super.onViewCreated(view, savedInstanceState)
        observeViewState()
        bind()
    }

    override fun onDestroy() {
        super.onDestroy()

        disposable?.dispose()
    }

    override fun androidInjector(): AndroidInjector<Any> = childFragmentInjector

    private fun observeViewState() {
        presenter.stateLiveData.observe(viewLifecycleOwner, ::render)
    }

    open fun bind() {}

    abstract fun render(viewState: VIEW_STATE)
}
