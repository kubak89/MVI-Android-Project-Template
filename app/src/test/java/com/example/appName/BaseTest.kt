package com.example.appName

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import io.reactivex.rxjava3.android.plugins.RxAndroidPlugins
import io.reactivex.rxjava3.internal.schedulers.TrampolineScheduler
import io.reactivex.rxjava3.plugins.RxJavaPlugins
import org.junit.Rule

abstract class BaseTest {
    @Rule
    @JvmField
    var testSchedulerRule = InstantTaskExecutorRule()

    init {
        RxAndroidPlugins.setInitMainThreadSchedulerHandler { TrampolineScheduler.instance() }
        RxJavaPlugins.setIoSchedulerHandler { TrampolineScheduler.instance() }
    }
}
