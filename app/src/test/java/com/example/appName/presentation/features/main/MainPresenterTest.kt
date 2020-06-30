package com.example.appName.presentation.features.main

import com.example.appName.BaseTest
import com.example.appName.data.model.ExampleUser
import com.example.appName.data.repository.exampleuser.ExampleUserRepository
import com.example.appName.presentation.features.main.MainConstants.LOGGED_OUT_NAME
import com.example.appName.utils.test
import io.reactivex.rxjava3.core.Single
import org.junit.Test

class MainPresenterTest : BaseTest() {
    private val exampleUserRepository: ExampleUserRepository = object : ExampleUserRepository {
        override fun getUser(): Single<ExampleUser> = Single.just(testUser)
    }

    private val testUser = ExampleUser("John")

    @Test
    fun `given logged out state, when login emits value then state contains logged in status and proper user name`() {
        // given
        val initialState = getWelcomeViewState()
        val mainPresenter = MainPresenter(initialState, exampleUserRepository)

        // when
        val testObserver = mainPresenter.stateLiveData.test()
        mainPresenter.acceptIntent(MainIntent.Login)

        // then
        testObserver.assertValues(
                getWelcomeViewState(),
                getLoggedViewState(testUser.name)
        )
    }

    @Test
    fun `given logged in state, when logout emits value then new state contains logged out status and default text`() {
        // given
        val initialState = getLoggedViewState(testUser.name)
        val mainPresenter = MainPresenter(initialState, exampleUserRepository)

        // when
        val testObserver = mainPresenter.stateLiveData.test()
        mainPresenter.acceptIntent(MainIntent.Logout)

        // then
        testObserver.assertValues(
                getLoggedViewState(testUser.name),
                getWelcomeViewState()
        )
    }

    private fun getWelcomeViewState() = MainViewState(LOGGED_OUT_NAME, false)
    private fun getLoggedViewState(userName: String) = MainViewState(userName, true)
}
