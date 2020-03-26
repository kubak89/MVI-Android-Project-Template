package com.example.appName.presentation.dummy

import android.os.Bundle
import android.view.View
import com.example.appName.R
import com.example.appName.presentation.base.BaseActivity
import io.reactivex.subjects.PublishSubject
import kotlinx.android.synthetic.main.activity_main.*

class DummyActivity : BaseActivity<DummyViewState, DummyPresenter>(
        R.layout.activity_main
), DummyView {
    //region Intents
    override val loginIntent: PublishSubject<Unit> = PublishSubject.create<Unit>()
    override val logoutIntent: PublishSubject<Unit> = PublishSubject.create<Unit>()
    //endregion

    //region Render methods
    override fun render(viewState: DummyViewState) {
        updateText(viewState)
        updateButtonsVisibility(viewState)
    }

    private fun updateText(viewState: DummyViewState) {
        main_text.text = viewState.mainText
    }

    private fun updateButtonsVisibility(viewState: DummyViewState) {
        login_button.visibility = if (viewState.isLoggedIn) {
            View.GONE
        } else {
            View.VISIBLE
        }

        logout_button.visibility = if (viewState.isLoggedIn) {
            View.VISIBLE
        } else {
            View.GONE
        }
    }
    //endregion

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindIntents()
    }

    private fun bindIntents() {
        login_button.setOnClickListener {
            loginIntent.onNext(Unit)
        }

        logout_button.setOnClickListener {
            logoutIntent.onNext(Unit)
        }
    }
}
