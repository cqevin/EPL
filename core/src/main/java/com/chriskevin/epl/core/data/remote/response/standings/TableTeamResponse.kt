package com.chriskevin.epl.core.data.remote.response.standings

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class TableTeamResponse(
    val id: Int,
    val name: String?,
    val crestUrl: String?
)