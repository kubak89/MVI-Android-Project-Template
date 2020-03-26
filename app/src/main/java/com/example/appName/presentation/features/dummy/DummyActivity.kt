package com.example.appName.presentation.features.dummy

import android.os.Bundle
import android.view.View
import com.example.appName.R
import com.example.appName.presentation.features.base.BaseActivity
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
        with(viewState) {
            updateText(mainText)
            updateButtonsVisibility(isLoggedIn)
        }
    }

    private fun updateText(text: String) {
        main_text.text = text
    }

    private fun updateButtonsVisibility(isLoggedIn: Boolean) {
        if (isLoggedIn) {
            login_button.visibility = View.GONE
            logout_button.visibility = View.VISIBLE
        } else {
            login_button.visibility = View.VISIBLE
            logout_button.visibility = View.GONE
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
