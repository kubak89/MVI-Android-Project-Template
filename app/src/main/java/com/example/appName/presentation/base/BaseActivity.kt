package com.example.appName.presentation.base

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import android.view.WindowManager
import com.example.appName.BuildConfig
import com.example.appName.presentation.di.ActivityModule
import com.example.appName.presentation.utils.ApplicationNavigator
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import java.io.Serializable
import javax.inject.Inject

const val KEY_SAVED_ACTIVITY_VIEW_STATE = "viewState"

const val TEXT_INPUT_TIMEOUT = 500L

abstract class BaseActivity<VIEW_STATE : Serializable, PRESENTER : BasePresenter<VIEW_STATE, *>> : AppCompatActivity() {
    private var disposable: Disposable? = null

    @Suppress("LeakingThis")
    val activityModule: ActivityModule = ActivityModule(this)

    @Inject
    lateinit var presenter: PRESENTER

    protected fun subscribeToViewState() {
        disposable = presenter.getStateObservable().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe(this::render)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean =
            when (item.itemId) {
                android.R.id.home -> {
                    onBackPressed()
                    true
                }
                else -> super.onOptionsItemSelected(item)
            }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

        outState.putSerializable(KEY_SAVED_ACTIVITY_VIEW_STATE, presenter.getCurrentViewState())
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (!BuildConfig.DEBUG) {
            window
                    .setFlags(WindowManager.LayoutParams.FLAG_SECURE,
                            WindowManager.LayoutParams.FLAG_SECURE)
        }
    }

    override fun onDestroy() {
        super.onDestroy()

        presenter.dispose()
        disposable?.dispose()
    }

    abstract fun render(viewState: VIEW_STATE)
}