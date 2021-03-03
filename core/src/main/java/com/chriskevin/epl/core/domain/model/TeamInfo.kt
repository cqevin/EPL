package com.chriskevin.epl.core.domain.model

data class TeamInfo(
    val id: Int,
    val name: String?,
    val tla: String?,
    val crestUrl: String?,
    val address: String?,
    val phone: String?,
    val website: String?,
    val email: String?,
    val founded: Int?,
    val venue: String?,
)