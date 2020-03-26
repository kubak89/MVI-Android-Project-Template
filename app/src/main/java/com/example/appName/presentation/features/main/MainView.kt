package com.example.appName.presentation.features.main

import io.reactivex.Observable

interface MainView {
    val loginIntent: Observable<Unit>
    val logoutIntent: Observable<Unit>
}
