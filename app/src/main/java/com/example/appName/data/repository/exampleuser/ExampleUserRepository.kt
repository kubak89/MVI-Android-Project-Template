package com.example.appName.data.repository.exampleuser

import com.example.appName.data.model.ExampleUser
import io.reactivex.rxjava3.core.Single

interface ExampleUserRepository {
    fun getUser(): Single<ExampleUser>
}
