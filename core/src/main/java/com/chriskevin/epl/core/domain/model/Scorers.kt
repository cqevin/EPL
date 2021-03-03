package com.chriskevin.epl.core.domain.model

data class Scorers(
    val position: Int,
    val player: ScorersPlayer,
    val team: ScorersTeam,
    val numberOfGoals: Int
)