package com.example.appName.data.repository.exampleuser

import com.example.appName.data.model.ExampleUser
import io.reactivex.Single

interface ExampleUserRepository {
    fun getUser(): Single<ExampleUser>
}
