package com.chriskevin.epl.core.data.local.room

import androidx.room.*
import com.chriskevin.epl.core.data.local.entity.FavoriteTeamEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoriteDao {
    @Query("SELECT * FROM favorite_team")
    fun getAllFavoriteTeam(): Flow<List<FavoriteTeamEntity>>

    @Query("SELECT * FROM favorite_team WHERE id=:id")
    fun getFavoriteTeam(id: Int): Flow<FavoriteTeamEntity?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(team: FavoriteTeamEntity)

    @Delete
    suspend fun delete(team: FavoriteTeamEntity)
}