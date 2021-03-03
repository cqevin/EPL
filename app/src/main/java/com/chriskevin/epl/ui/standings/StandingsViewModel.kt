package com.chriskevin.epl.ui.standings

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.chriskevin.epl.core.data.Resource
import com.chriskevin.epl.core.domain.model.Table
import com.chriskevin.epl.core.domain.usecase.AppUseCase
import com.chriskevin.epl.util.Event
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class StandingsViewModel @Inject constructor(private val appUseCase: AppUseCase) : ViewModel() {

    private val _listItem: MutableLiveData<List<Table>> by lazy {
        MutableLiveData<List<Table>>().also { getData() }
    }
    private val _error = MutableLiveData<Event<Boolean>>()

    val listItem: LiveData<List<Table>> = _listItem
    val error: LiveData<Event<Boolean>> = _error

    private fun getData() = viewModelScope.launch {
        appUseCase.getStandings().collect {
            when (it) {
                is Resource.Success -> _listItem.value = it.data
                is Resource.Error -> {
                    _listItem.value = listOf()
                    _error.value = Event(true)
                }
            }
        }
    }
}