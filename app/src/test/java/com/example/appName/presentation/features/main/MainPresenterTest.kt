package com.example.appName.presentation.features.main

import com.example.appName.data.model.ExampleUser
import com.example.appName.data.repository.dummyuser.ExampleUserRepository
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.subjects.PublishSubject
import org.junit.Before
import org.junit.Test

class MainPresenterTest {
    private lateinit var exampleUserRepository: ExampleUserRepository
    private val testUser = ExampleUser("John")
    private val view: MainView = object : MainView {
        override val loginIntent: Observable<Unit> = PublishSubject.create()
        override val logoutIntent: Observable<Unit> = PublishSubject.create()
    }

    @Before
    fun setUp() {
        exampleUserRepository = object : ExampleUserRepository {
            override fun getUser(): Single<ExampleUser> = Single.just(testUser)
        }
    }

    @Test
    fun `given logged out state, when login emits value then state contains logged in status and proper user name`() {
        // given
        val initialState = getWelcomeViewState()
        val mainPresenter = MainPresenter(view, initialState, exampleUserRepository)

        // when
        val testObserver = mainPresenter.stateObservable.test()
        (view.loginIntent as PublishSubject).onNext(Unit)

        // then
        testObserver
                .assertValues(
                        getWelcomeViewState(),
                        getLoggedViewState(testUser.name)
                )
                .dispose()
    }

    @Test
    fun `given logged in state, when logout emits value then new state contains logged out status and default text`() {
        // given
        val initialState = getLoggedViewState(testUser.name)
        val mainPresenter = MainPresenter(view, initialState, exampleUserRepository)

        // when
        val testObserver = mainPresenter.stateObservable.test()
        (view.logoutIntent as PublishSubject).onNext(Unit)

        // then
        testObserver
                .assertValues(
                        getLoggedViewState(testUser.name),
                        getWelcomeViewState()
                ).dispose()
    }

    private fun getWelcomeViewState() = MainViewState("Welcome Stranger!", false)
    private fun getLoggedViewState(userName: String) = MainViewState("Welcome $userName!", true)
}
