package com.chriskevin.epl.core.di

import android.content.Context
import androidx.room.Room
import com.chriskevin.epl.core.data.local.room.AppDatabase
import com.chriskevin.epl.core.data.local.room.FavoriteDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import net.sqlcipher.database.SQLiteDatabase
import net.sqlcipher.database.SupportFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase {
        val passphrase: ByteArray = SQLiteDatabase.getBytes("ipiel".toCharArray())
        val factory = SupportFactory(passphrase)
        return Room.databaseBuilder(
            context, AppDatabase::class.java, "epl.db"
        ).openHelperFactory(factory).build()
    }

    @Provides
    fun provideFavoriteDao(database: AppDatabase): FavoriteDao = database.favoriteDao()
}