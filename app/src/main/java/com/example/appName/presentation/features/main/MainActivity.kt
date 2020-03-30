package com.example.appName.presentation.features.main

import android.view.View
import com.example.appName.R
import com.example.appName.presentation.features.base.BaseActivity
import com.jakewharton.rxbinding3.view.clicks
import io.reactivex.Observable
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity<MainViewState, MainPresenter>(
        R.layout.activity_main
), MainView {
    //region Intents
    override val loginIntent: Observable<Unit>
        get() = login_button.clicks()
    override val logoutIntent: Observable<Unit>
        get() = logout_button.clicks()
    //endregion

    //region Render methods
    override fun render(viewState: MainViewState) {
        with(viewState) {
            renderText(mainText)
            renderButtonsVisibility(isLoggedIn)
        }
    }

    private fun renderText(text: String) {
        main_text.text = text
    }

    private fun renderButtonsVisibility(isLoggedIn: Boolean) {
        if (isLoggedIn) {
            login_button.visibility = View.GONE
            logout_button.visibility = View.VISIBLE
        } else {
            login_button.visibility = View.VISIBLE
            logout_button.visibility = View.GONE
        }
    }
    //endregion
}
