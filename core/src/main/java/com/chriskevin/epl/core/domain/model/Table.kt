package com.chriskevin.epl.core.domain.model

data class Table(
    val position: Int,
    val team: TableTeam,
    val playedGames: String,
    val goalDifference: String,
    val points: String
)