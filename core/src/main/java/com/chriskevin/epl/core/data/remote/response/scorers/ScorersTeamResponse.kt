package com.chriskevin.epl.core.data.remote.response.scorers

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ScorersTeamResponse(val id: Int, val name: String)