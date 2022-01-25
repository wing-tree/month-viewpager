package com.wing.tree.month.viewpager.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.wing.tree.month.viewpager.domain.repository.instance.InstancesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MonthViewModel @Inject constructor(private val repository: InstancesRepository) : ViewModel() {
    fun instances(year: Int, month: Int) = repository.instances(
        year = year,
        month = month
    ).asLiveData(viewModelScope.coroutineContext)
}