package com.example.appName.data.repository.exampleuser

import com.example.appName.data.model.ExampleUser
import com.example.base.utils.SchedulersFactory
import io.reactivex.rxjava3.core.Single

class LocalExampleUserRepository : ExampleUserRepository {
    override fun getUser(): Single<ExampleUser> {
        return Single
                .just(ExampleUser(LOCAL_USER_NAME))
                .subscribeOn(SchedulersFactory.io)
    }

    companion object {
        const val LOCAL_USER_NAME = "Local User"
    }
}
