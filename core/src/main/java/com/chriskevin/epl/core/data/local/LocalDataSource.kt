package com.chriskevin.epl.core.data.local

import com.chriskevin.epl.core.data.local.entity.FavoriteTeamEntity
import com.chriskevin.epl.core.data.local.room.FavoriteDao
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LocalDataSource @Inject constructor(private val favoriteDao: FavoriteDao) {
    fun getAllFavoriteTeam() = favoriteDao.getAllFavoriteTeam()

    fun getFavoriteTeam(id: Int) = favoriteDao.getFavoriteTeam(id)

    suspend fun insertFavoriteTeam(team: FavoriteTeamEntity) = favoriteDao.insert(team)

    suspend fun deleteFavoriteTeam(team: FavoriteTeamEntity) = favoriteDao.delete(team)
}