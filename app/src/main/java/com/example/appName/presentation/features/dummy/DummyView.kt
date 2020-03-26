package com.example.appName.presentation.features.dummy

import io.reactivex.Observable

interface DummyView {
    val loginIntent: Observable<Unit>
    val logoutIntent: Observable<Unit>
}
