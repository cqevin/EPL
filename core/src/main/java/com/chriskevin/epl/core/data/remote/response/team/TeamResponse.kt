package com.chriskevin.epl.core.data.remote.response.team

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class TeamResponse(
    val id: Int,
    val name: String,
    val tla: String?,
    val crestUrl: String?,
    val address: String?,
    val phone: String?,
    val website: String?,
    val email: String?,
    val founded: Int?,
    val venue: String?,
    val squad: List<TeamSquadResponse>?
)