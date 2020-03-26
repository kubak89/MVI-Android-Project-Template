package com.example.appName.data.repository.exampleuser

import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class ExampleUserModule {

    @Provides
    @Singleton
    fun provideExampleUserRepository(): ExampleUserRepository = LocalExampleUserRepository()
}
