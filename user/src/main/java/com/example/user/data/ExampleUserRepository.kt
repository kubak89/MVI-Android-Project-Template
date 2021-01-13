package com.example.user.data

import com.example.user.model.ExampleUser
import io.reactivex.rxjava3.core.Single

interface ExampleUserRepository {
    fun getUser(): Single<ExampleUser>
}
