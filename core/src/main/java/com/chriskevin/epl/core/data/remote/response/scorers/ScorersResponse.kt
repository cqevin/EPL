package com.chriskevin.epl.core.data.remote.response.scorers

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ScorersResponse(
    val player: ScorersPlayerResponse,
    val team: ScorersTeamResponse,
    val numberOfGoals: Int
)