package com.example.appName.presentation.base

import android.app.Activity
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import android.view.View
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import com.example.appName.BuildConfig
import com.example.appName.presentation.di.ActivityModule
import com.example.appName.presentation.utils.ApplicationNavigator
import com.jakewharton.rxbinding2.widget.RxTextView
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import java.io.Serializable
import java.util.concurrent.TimeUnit
import javax.inject.Inject

const val KEY_SAVED_ACTIVITY_VIEW_STATE = "viewState"

const val TEXT_INPUT_TIMEOUT = 500L

abstract class BaseActivity<VIEW_STATE : Serializable, PRESENTER : Presenter<VIEW_STATE, *>> : AppCompatActivity(), NavigatorActivity {
    protected val disposables: CompositeDisposable = CompositeDisposable()

    @Suppress("LeakingThis")
    val activityModule: ActivityModule = ActivityModule(this)

    @Inject
    lateinit var presenter: PRESENTER

    @Inject
    override lateinit var applicationNavigator: ApplicationNavigator

    protected fun subscribeToViewState() {
        val viewStateObservable = presenter.getStateObservable().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe(this::render)

        disposables.add(viewStateObservable)
    }

    protected fun hideKeyboard() {
        val view: View = currentFocus ?: View(this)
        val inputMethodManager = getSystemService(
                Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean =
            when (item.itemId) {
                android.R.id.home -> {
                    onBackPressed()
                    true
                }
                else -> super.onOptionsItemSelected(item)
            }

    abstract fun render(viewState: VIEW_STATE)

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
        disposables.dispose()
    }

    protected fun prepareTextInputObservable(editText: EditText): Observable<String> =
            RxTextView
                    .textChanges(editText)
                    .skip(1)
                    .map { it.toString() }
                    .debounce(TEXT_INPUT_TIMEOUT, TimeUnit.MILLISECONDS)
}