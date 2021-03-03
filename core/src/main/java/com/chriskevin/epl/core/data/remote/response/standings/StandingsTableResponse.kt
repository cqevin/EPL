package com.chriskevin.epl.core.data.remote.response.standings

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class StandingsTableResponse(
    val position: Int,
    val team: TableTeamResponse,
    val playedGames: Int,
    val goalDifference: Int,
    val points: Int
)