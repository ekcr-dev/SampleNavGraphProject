package com.example.examplenavgraphbugproject

import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AddlModule {
    @Provides
    @Singleton
    fun provideSchedulers(): SchedulerProvider {
        return ProdSchedulerProvider()
    }
}