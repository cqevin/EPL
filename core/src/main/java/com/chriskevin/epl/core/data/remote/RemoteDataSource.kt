package com.chriskevin.epl.core.data.remote

import com.chriskevin.epl.core.data.remote.response.scorers.ScorersObjResponse
import com.chriskevin.epl.core.data.remote.response.standings.StandingsObjResponse
import com.chriskevin.epl.core.data.remote.response.team.TeamResponse
import com.chriskevin.epl.core.data.remote.retrofit.ApiResponse
import com.chriskevin.epl.core.data.remote.retrofit.ApiService
import com.chriskevin.epl.core.util.EspressoIdlingResource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RemoteDataSource @Inject constructor(private val apiService: ApiService) {

    suspend fun getStandings(): Flow<ApiResponse<StandingsObjResponse>> = flow {
        EspressoIdlingResource.increment()
        try {
            emit(ApiResponse.call(apiService.getStandings()))
        } catch (ex: Exception) {
            Timber.tag("RemoteDataSource").e(ex)
            emit(ApiResponse.Error(ex.message.toString()))
        }
        EspressoIdlingResource.decrement()
    }

    suspend fun getScorers(): Flow<ApiResponse<ScorersObjResponse>> = flow {
        EspressoIdlingResource.increment()
        try {
            emit(ApiResponse.call(apiService.getScorers()))
        } catch (ex: Exception) {
            Timber.tag("RemoteDataSource").e(ex)
            emit(ApiResponse.Error(ex.message.toString()))
        }
        EspressoIdlingResource.decrement()
    }

    suspend fun getTeamDetails(id: Int): Flow<ApiResponse<TeamResponse>> = flow {
        EspressoIdlingResource.increment()
        try {
            emit(ApiResponse.call(apiService.getTeamDetails(id)))
        } catch (ex: Exception) {
            Timber.tag("RemoteDataSource").e(ex)
            emit(ApiResponse.Error(ex.message.toString()))
        }
        EspressoIdlingResource.decrement()
    }
}