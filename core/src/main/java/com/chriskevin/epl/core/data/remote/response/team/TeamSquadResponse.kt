package com.chriskevin.epl.core.data.remote.response.team

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class TeamSquadResponse(
    val id: Int,
    val name: String?,
    val position: String?,
    val nationality: String?,
    val role: String?
)