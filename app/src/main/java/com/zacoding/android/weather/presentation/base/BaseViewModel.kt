package com.zacoding.android.weather.presentation.base

import androidx.lifecycle.ViewModel
import com.zacoding.android.weather.domain.use_case.FlowUseCase
import kotlinx.coroutines.flow.StateFlow

abstract class BaseViewModel(
    private vararg val useCases: FlowUseCase<*, *>?
) : ViewModel() {

    abstract val state: StateFlow<ViewState>

    override fun onCleared() {
        useCases.forEach { it?.onCleared() }
        super.onCleared()
    }
}
