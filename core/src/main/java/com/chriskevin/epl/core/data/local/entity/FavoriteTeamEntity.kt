package com.chriskevin.epl.core.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorite_team")
data class FavoriteTeamEntity(@PrimaryKey val id: Int, val name: String?, val crestUrl: String?)