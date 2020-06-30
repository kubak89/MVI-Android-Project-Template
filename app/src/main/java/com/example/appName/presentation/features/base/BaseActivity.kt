package com.example.appName.presentation.features.base

import android.os.Bundle
import android.view.MenuItem
import android.view.WindowManager
import androidx.annotation.CallSuper
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.observe
import com.example.appName.BuildConfig
import dagger.android.AndroidInjection
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import io.reactivex.rxjava3.disposables.Disposable
import java.io.Serializable
import javax.inject.Inject

abstract class BaseActivity<VIEW_STATE : Serializable, PRESENTER : BasePresenter<VIEW_STATE, *, *>>(
        @LayoutRes val layoutId: Int
) : AppCompatActivity(), HasAndroidInjector {

    @Inject
    lateinit var presenter: PRESENTER

    @Inject
    lateinit var fragmentInjector: DispatchingAndroidInjector<Any>

    private var disposable: Disposable? = null

    override fun onOptionsItemSelected(item: MenuItem): Boolean =
            when (item.itemId) {
                android.R.id.home -> {
                    onBackPressed()
                    true
                }
                else -> super.onOptionsItemSelected(item)
            }

    @CallSuper
    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)

        inflateView()
        observeViewState()

        if (!BuildConfig.DEBUG) {
            window.setFlags(
                    WindowManager.LayoutParams.FLAG_SECURE,
                    WindowManager.LayoutParams.FLAG_SECURE
            )
        }
    }

    override fun androidInjector(): AndroidInjector<Any> = fragmentInjector

    override fun onDestroy() {
        super.onDestroy()

        disposable?.dispose()
    }

    private fun observeViewState() {
        presenter.stateLiveData.observe(this, ::render)
    }

    private fun inflateView() {
        setContentView(layoutId)
    }

    abstract fun render(viewState: VIEW_STATE)
}
