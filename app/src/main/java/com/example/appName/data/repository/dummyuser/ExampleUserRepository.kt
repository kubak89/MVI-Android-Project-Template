package com.example.appName.data.repository.dummyuser

import com.example.appName.data.model.ExampleUser
import io.reactivex.Single

interface ExampleUserRepository {
    fun getUser(): Single<ExampleUser>
}
