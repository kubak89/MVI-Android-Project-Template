package com.example.appName.presentation.features.base

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Scheduler
import io.reactivex.rxjava3.schedulers.Schedulers
import java.util.concurrent.Executors

interface SchedulersFactory {

    val io: Scheduler
    val main: Scheduler
    val reducer: Scheduler

    companion object : SchedulersFactory {
        private var instance: SchedulersFactory = Default()

        class Default : SchedulersFactory {

            private val reducerExecutor = Executors.newSingleThreadExecutor()

            override val io
                get() = Schedulers.io()
            override val main
                get() = AndroidSchedulers.mainThread()
            override val reducer: Scheduler
                get() = Schedulers.from(reducerExecutor)
        }


        //for test purposes
        fun set(factory: SchedulersFactory) {
            instance = factory
        }

        override val io
            get() = instance.io
        override val main
            get() = instance.main
        override val reducer: Scheduler
            get() = instance.reducer
    }
}