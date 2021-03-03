package com.chriskevin.epl.favorite

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.chriskevin.epl.core.domain.model.FavoriteTeam
import com.chriskevin.epl.core.domain.usecase.AppUseCase

class FavoriteViewModel(appUseCase: AppUseCase) : ViewModel() {

    val listItem: LiveData<List<FavoriteTeam>> = appUseCase.getAllFavoriteTeam().asLiveData()

}