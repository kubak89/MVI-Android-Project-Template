package com.example.appName.presentation.features.main

import io.reactivex.rxjava3.core.Observable

interface MainView {
    val loginIntent: Observable<Unit>
    val logoutIntent: Observable<Unit>
}
