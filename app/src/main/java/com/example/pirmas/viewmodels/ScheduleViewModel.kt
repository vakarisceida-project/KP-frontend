package com.example.pirmas.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pirmas.ApiService
import com.example.pirmas.data.WorkoutResponse
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ScheduleViewModel : ViewModel() {

    private val _schedule = MutableStateFlow<List<WorkoutResponse>>(emptyList())
    val schedule: StateFlow<List<WorkoutResponse>> = _schedule

    private val apiService = ApiService.create()

    init {
        getSchedule()
    }

    private fun getSchedule() {
        viewModelScope.launch {
            val response = apiService.getSchedule()
            if (response.isSuccessful) {
                _schedule.value = response.body()?.schedule ?: emptyList()
            }
        }
    }
}
