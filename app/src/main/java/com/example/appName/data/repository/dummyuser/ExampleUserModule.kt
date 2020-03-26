package com.example.appName.data.repository.dummyuser

import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class ExampleUserModule {

    @Provides
    @Singleton
    fun provideDummyUserRepository(): ExampleUserRepository = LocalExampleUserRepository()
}
