package com.chriskevin.epl.favorite

import android.content.Context
import com.chriskevin.epl.di.FavoriteModuleDependency
import dagger.BindsInstance
import dagger.Component

@Component(dependencies = [FavoriteModuleDependency::class])
interface FavoriteComponent {

    fun inject(fragment: FavoriteFragment)

    @Component.Builder
    interface Builder {
        fun context(@BindsInstance context: Context): Builder
        fun appDependencies(favoriteModuleDependencies: FavoriteModuleDependency): Builder
        fun build(): FavoriteComponent
    }
}