package com.chriskevin.epl.core.data

import com.chriskevin.epl.core.data.local.LocalDataSource
import com.chriskevin.epl.core.data.remote.RemoteDataSource
import com.chriskevin.epl.core.data.remote.retrofit.ApiResponse
import com.chriskevin.epl.core.domain.model.FavoriteTeam
import com.chriskevin.epl.core.domain.model.Scorers
import com.chriskevin.epl.core.domain.model.Table
import com.chriskevin.epl.core.domain.model.TeamDetails
import com.chriskevin.epl.core.domain.repository.AppRepository
import com.chriskevin.epl.core.util.DataMapper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AppRepositoryImpl @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource
) : AppRepository {

    override suspend fun getStandings(): Flow<Resource<List<Table>>> =
        remoteDataSource.getStandings()
            .map {
                when (it) {
                    is ApiResponse.Success ->
                        Resource.Success(DataMapper.mapStandingsTableResponseToDomain(it.data.standings[0].table))
                    is ApiResponse.Empty -> Resource.Success(listOf())
                    is ApiResponse.Error -> Resource.Error(it.errorMessage)
                }
            }
            .flowOn(Dispatchers.Default)

    override suspend fun getScorers(): Flow<Resource<List<Scorers>>> =
        remoteDataSource.getScorers()
            .map {
                when (it) {
                    is ApiResponse.Success ->
                        Resource.Success(DataMapper.mapScorersResponseToDomain(it.data.scorers))
                    is ApiResponse.Empty -> Resource.Success(listOf())
                    is ApiResponse.Error -> Resource.Error(it.errorMessage)
                }
            }
            .flowOn(Dispatchers.Default)

    override suspend fun getTeamDetails(id: Int): Flow<Resource<TeamDetails>> =
        remoteDataSource.getTeamDetails(id)
            .map {
                when (it) {
                    is ApiResponse.Success ->
                        Resource.Success(DataMapper.mapTeamResponseToDomain(it.data))
                    is ApiResponse.Empty -> Resource.Error("empty")
                    is ApiResponse.Error -> Resource.Error(it.errorMessage)
                }
            }
            .flowOn(Dispatchers.Default)

    override fun getAllFavoriteTeam(): Flow<List<FavoriteTeam>> =
        localDataSource.getAllFavoriteTeam().map {
            DataMapper.mapListFavoriteTeamEntityToDomain(it)
        }.flowOn(Dispatchers.Default)

    override fun getFavoriteTeam(id: Int): Flow<FavoriteTeam?> =
        localDataSource.getFavoriteTeam(id).map {
            DataMapper.mapFavoriteTeamEntityToDomain(it)
        }

    override suspend fun insertFavoriteTeam(team: FavoriteTeam) =
        localDataSource.insertFavoriteTeam(DataMapper.mapFavoriteTeamToEntity(team))

    override suspend fun deleteFavoriteTeam(team: FavoriteTeam) =
        localDataSource.deleteFavoriteTeam(DataMapper.mapFavoriteTeamToEntity(team))
}