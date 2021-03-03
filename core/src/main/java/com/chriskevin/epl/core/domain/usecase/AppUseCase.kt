package com.chriskevin.epl.core.domain.usecase

import com.chriskevin.epl.core.data.Resource
import com.chriskevin.epl.core.domain.model.FavoriteTeam
import com.chriskevin.epl.core.domain.model.Scorers
import com.chriskevin.epl.core.domain.model.Table
import com.chriskevin.epl.core.domain.model.TeamDetails
import kotlinx.coroutines.flow.Flow

interface AppUseCase {
    suspend fun getStandings(): Flow<Resource<List<Table>>>
    suspend fun getScorers(): Flow<Resource<List<Scorers>>>
    suspend fun getTeamDetails(id: Int): Flow<Resource<TeamDetails>>
    fun getAllFavoriteTeam(): Flow<List<FavoriteTeam>>
    fun getFavoriteTeam(id: Int): Flow<FavoriteTeam?>
    suspend fun insertFavoriteTeam(team: FavoriteTeam)
    suspend fun deleteFavoriteTeam(team: FavoriteTeam)
}