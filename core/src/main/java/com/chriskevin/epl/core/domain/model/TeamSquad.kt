package com.chriskevin.epl.core.domain.model

import android.os.Parcelable
import androidx.annotation.Keep
import kotlinx.parcelize.Parcelize

@Keep
@Parcelize
data class TeamSquad(
    val id: Int,
    val name: String?,
    val position: String?,
    val nationality: String?,
    val role: String?
) : Parcelable