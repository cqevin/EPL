package com.chriskevin.epl.favorite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.chriskevin.epl.core.domain.usecase.AppUseCase
import javax.inject.Inject

class ViewModelFactory @Inject constructor(private val appUseCase: AppUseCase) :
    ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        try {
            return modelClass.getConstructor(AppUseCase::class.java)
                .newInstance(appUseCase)
        } catch (e: InstantiationException) {
            throw RuntimeException("Cannot create an instance of $modelClass", e)
        } catch (e: IllegalAccessException) {
            throw RuntimeException("Cannot create an instance of $modelClass", e)
        }
    }
}