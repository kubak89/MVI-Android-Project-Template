package com.example.appName.presentation.main

import android.os.Bundle
import com.example.appName.R
import com.example.appName.presentation.base.BaseActivity

class MainActivity : BaseActivity<MainViewState, MainPresenter>(), MainView {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        DaggerMainComponent.builder()
                .mainModule(MainModule(this)).build().inject(this)

        subscribeToViewState()
    }

    override fun render(viewState: MainViewState) = TODO("Implement rendering")
}
