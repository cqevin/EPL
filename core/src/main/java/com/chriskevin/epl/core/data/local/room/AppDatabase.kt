package com.chriskevin.epl.core.data.local.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.chriskevin.epl.core.data.local.entity.FavoriteTeamEntity

@Database(entities = [FavoriteTeamEntity::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun favoriteDao(): FavoriteDao
}