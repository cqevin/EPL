package com.chriskevin.epl.core.data.remote.retrofit

import com.chriskevin.epl.core.BuildConfig
import com.chriskevin.epl.core.data.remote.response.scorers.ScorersObjResponse
import com.chriskevin.epl.core.data.remote.response.standings.StandingsObjResponse
import com.chriskevin.epl.core.data.remote.response.team.TeamResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path

interface ApiService {
    @Headers("X-Auth-Token: ${BuildConfig.apiKey}")
    @GET("competitions/2021/standings")
    suspend fun getStandings(): Response<StandingsObjResponse>

    @Headers("X-Auth-Token: ${BuildConfig.apiKey}")
    @GET("competitions/2021/scorers")
    suspend fun getScorers(): Response<ScorersObjResponse>

    @Headers("X-Auth-Token: ${BuildConfig.apiKey}")
    @GET("teams/{id}")
    suspend fun getTeamDetails(@Path("id") id: Int): Response<TeamResponse>
}