package com.chriskevin.epl.ui.teamdetails

import androidx.lifecycle.*
import com.chriskevin.epl.core.data.Resource
import com.chriskevin.epl.core.domain.model.FavoriteTeam
import com.chriskevin.epl.core.domain.model.TeamDetails
import com.chriskevin.epl.core.domain.usecase.AppUseCase
import com.chriskevin.epl.util.Const.TEAM_ID
import com.chriskevin.epl.util.Event
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TeamDetailsViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val appUseCase: AppUseCase
) : ViewModel() {
    private val _showContent = MutableLiveData<Event<Boolean>>()
    private val _favoriteStatus = MutableLiveData<Event<Boolean>>()

    init {
        _showContent.value = Event(false)
    }

    private val teamId = savedStateHandle.get<Int>(TEAM_ID)
    private val _details: MutableLiveData<TeamDetails> by lazy {
        MutableLiveData<TeamDetails>().also { getData(teamId) }
    }

    val details: LiveData<TeamDetails> = _details
    val showContent: LiveData<Event<Boolean>> = _showContent
    val favoriteStatus: LiveData<Event<Boolean>> = _favoriteStatus

    private fun getData(id: Int?) = viewModelScope.launch {
        id?.let { id ->
            appUseCase.getTeamDetails(id).collect {
                when (it) {
                    is Resource.Success -> {
                        _details.value = it.data
                        _showContent.value = Event(true)
                    }
                    is Resource.Error -> _showContent.value = Event(true)
                }
            }
        }
    }

    val getFavoriteTeam = teamId?.let { appUseCase.getFavoriteTeam(teamId).asLiveData() }

    fun insertFavorite() = viewModelScope.launch {
        _details.value?.let {
            val team = FavoriteTeam(it.teamInfo.id, it.teamInfo.name, it.teamInfo.crestUrl)
            appUseCase.insertFavoriteTeam(team)
            _favoriteStatus.value = Event(true)
        }
    }

    fun deleteFavorite() = viewModelScope.launch {
        _details.value?.let {
            val team = FavoriteTeam(it.teamInfo.id, it.teamInfo.name, it.teamInfo.crestUrl)
            appUseCase.deleteFavoriteTeam(team)
            _favoriteStatus.value = Event(false)
        }
    }
}