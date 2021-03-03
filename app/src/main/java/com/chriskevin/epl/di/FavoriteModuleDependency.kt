package com.chriskevin.epl.di

import com.chriskevin.epl.core.domain.usecase.AppUseCase
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@EntryPoint
@InstallIn(SingletonComponent::class)
interface FavoriteModuleDependency {

    fun provideAppUseCase(): AppUseCase
}