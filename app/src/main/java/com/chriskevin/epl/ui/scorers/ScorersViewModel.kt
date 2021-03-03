package com.chriskevin.epl.ui.scorers

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.chriskevin.epl.core.data.Resource
import com.chriskevin.epl.core.domain.model.Scorers
import com.chriskevin.epl.core.domain.usecase.AppUseCase
import com.chriskevin.epl.util.Event
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ScorersViewModel @Inject constructor(private val appUseCase: AppUseCase) : ViewModel() {
    private val _listItem: MutableLiveData<List<Scorers>> by lazy {
        MutableLiveData<List<Scorers>>().also { getData() }
    }
    private val _error = MutableLiveData<Event<Boolean>>()

    val listItem: LiveData<List<Scorers>> = _listItem
    val error: LiveData<Event<Boolean>> = _error

    private fun getData() = viewModelScope.launch {
        appUseCase.getScorers().collect {
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