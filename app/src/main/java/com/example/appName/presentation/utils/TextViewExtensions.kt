package com.example.appName.presentation.utils

import android.widget.TextView
import com.example.appName.presentation.base.TEXT_INPUT_TIMEOUT
import com.jakewharton.rxbinding2.widget.RxTextView
import io.reactivex.Observable
import java.util.concurrent.TimeUnit

fun TextView.prepareTextInputObservable(): Observable<String> =
        RxTextView
                .textChanges(this)
                .skip(1)
                .map { it.toString() }
                .debounce(TEXT_INPUT_TIMEOUT, TimeUnit.MILLISECONDS)