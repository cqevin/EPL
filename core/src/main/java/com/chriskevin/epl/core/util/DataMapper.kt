package com.chriskevin.epl.core.util

import com.chriskevin.epl.core.data.local.entity.FavoriteTeamEntity
import com.chriskevin.epl.core.data.remote.response.scorers.ScorersResponse
import com.chriskevin.epl.core.data.remote.response.standings.StandingsTableResponse
import com.chriskevin.epl.core.data.remote.response.team.TeamResponse
import com.chriskevin.epl.core.domain.model.*

object DataMapper {
    fun mapStandingsTableResponseToDomain(input: List<StandingsTableResponse>): List<Table> =
        input.map {
            Table(
                position = it.position,
                team = TableTeam(id = it.team.id, name = it.team.name, crestUrl = it.team.crestUrl),
                playedGames = it.playedGames,
                goalDifference = it.goalDifference,
                points = it.points
            )
        }

    fun mapScorersResponseToDomain(input: List<ScorersResponse>): List<Scorers> =
        input.mapIndexed { idx, scorer ->
            Scorers(
                position = idx + 1,
                player = ScorersPlayer(scorer.player.id, scorer.player.name),
                team = ScorersTeam(scorer.team.id, scorer.team.name),
                numberOfGoals = scorer.numberOfGoals
            )
        }

    fun mapTeamResponseToDomain(input: TeamResponse): TeamDetails =
        TeamDetails(
            TeamInfo(
                id = input.id,
                name = input.name,
                tla = input.tla,
                crestUrl = input.crestUrl,
                address = input.address,
                phone = input.phone,
                website = input.website,
                email = input.email,
                founded = input.founded,
                venue = input.venue
            ),
            input.squad?.map {
                TeamSquad(
                    id = it.id,
                    name = it.name,
                    position = it.position,
                    nationality = it.nationality,
                    role = it.role
                )
            }
        )

    fun mapListFavoriteTeamEntityToDomain(input: List<FavoriteTeamEntity>): List<FavoriteTeam> =
        input.map {
            FavoriteTeam(id = it.id, name = it.name, crestUrl = it.crestUrl)
        }

    fun mapFavoriteTeamToEntity(input: FavoriteTeam): FavoriteTeamEntity =
        FavoriteTeamEntity(id = input.id, name = input.name, crestUrl = input.crestUrl)

    fun mapFavoriteTeamEntityToDomain(input: FavoriteTeamEntity?): FavoriteTeam? =
        input?.let { FavoriteTeam(id = it.id, name = it.name, crestUrl = it.crestUrl) }
}