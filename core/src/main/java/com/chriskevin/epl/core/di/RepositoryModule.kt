package com.chriskevin.epl.core.di

import com.chriskevin.epl.core.data.AppRepositoryImpl
import com.chriskevin.epl.core.domain.repository.AppRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module(includes = [NetworkModule::class, DatabaseModule::class])
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun provideRepository(appRepository: AppRepositoryImpl): AppRepository

}