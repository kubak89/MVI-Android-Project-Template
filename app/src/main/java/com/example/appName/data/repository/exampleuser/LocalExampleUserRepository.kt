package com.example.appName.data.repository.exampleuser

import com.example.appName.data.model.ExampleUser
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers

class LocalExampleUserRepository : ExampleUserRepository {
    override fun getUser(): Single<ExampleUser> {
        return Single.just(ExampleUser(LOCAL_USER_NAME)).subscribeOn(Schedulers.io())
    }

    companion object {
        const val LOCAL_USER_NAME = "Local User"
    }
}
