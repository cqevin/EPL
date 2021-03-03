package com.chriskevin.epl.core.domain.model

data class Table(
    val position: Int,
    val team: TableTeam,
    val playedGames: Int,
    val goalDifference: Int,
    val points: Int
)