package com.example.appName.presentation.features.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import androidx.lifecycle.observe
import io.reactivex.rxjava3.disposables.Disposable
import java.io.Serializable

abstract class BaseFragment<VIEW_STATE : Serializable, PRESENTER : BasePresenter<VIEW_STATE, *, *>>(
        @LayoutRes val layoutId: Int
) : Fragment() {

    abstract val presenter: PRESENTER

    private var disposable: Disposable? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
            inflater.inflate(layoutId, null, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeViewState()
        bind()
    }

    override fun onDestroy() {
        super.onDestroy()

        disposable?.dispose()
    }

    private fun observeViewState() {
        presenter.stateLiveData.observe(viewLifecycleOwner, ::render)
    }

    open fun bind() {}

    abstract fun render(viewState: VIEW_STATE)
}
