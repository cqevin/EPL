package com.chriskevin.epl.core.data.remote.response.standings

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class StandingsObjResponse(val standings: List<StandingsResponse>)