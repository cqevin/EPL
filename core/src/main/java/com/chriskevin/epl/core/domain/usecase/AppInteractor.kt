package com.chriskevin.epl.core.domain.usecase

import com.chriskevin.epl.core.domain.model.FavoriteTeam
import com.chriskevin.epl.core.domain.repository.AppRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class AppInteractor @Inject constructor(private val appRepository: AppRepository) : AppUseCase {
    override suspend fun getStandings() = appRepository.getStandings()
    override suspend fun getScorers() = appRepository.getScorers()
    override suspend fun getTeamDetails(id: Int) = appRepository.getTeamDetails(id)
    override fun getAllFavoriteTeam(): Flow<List<FavoriteTeam>> = appRepository.getAllFavoriteTeam()
    override fun getFavoriteTeam(id: Int): Flow<FavoriteTeam?> = appRepository.getFavoriteTeam(id)
    override suspend fun insertFavoriteTeam(team: FavoriteTeam) =
        appRepository.insertFavoriteTeam(team)
    override suspend fun deleteFavoriteTeam(team: FavoriteTeam) =
        appRepository.deleteFavoriteTeam(team)
}