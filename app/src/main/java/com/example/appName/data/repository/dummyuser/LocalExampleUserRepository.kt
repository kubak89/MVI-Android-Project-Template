package com.example.appName.data.repository.dummyuser

import com.example.appName.data.model.ExampleUser
import io.reactivex.Single

class LocalExampleUserRepository : ExampleUserRepository {
    override fun getUser(): Single<ExampleUser> {
        return Single.just(ExampleUser(LOCAL_USER_NAME))
    }

    companion object {
        const val LOCAL_USER_NAME = "Local User"
    }
}
