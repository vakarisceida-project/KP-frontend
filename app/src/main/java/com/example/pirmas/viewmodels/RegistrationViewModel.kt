package com.example.pirmas.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pirmas.ApiService
import com.example.pirmas.data.ScheduleDayRequest
import com.example.pirmas.data.UserProfileRequest
import com.example.pirmas.ui.screens.ScheduleDay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class RegistrationViewModel : ViewModel() {

    private val _weight = MutableStateFlow("")
    val weight: StateFlow<String> = _weight

    private val _height = MutableStateFlow("")
    val height: StateFlow<String> = _height

    private val _schedule = MutableStateFlow(
        listOf("P", "A", "T", "K", "Pn", "Š", "S").map { ScheduleDay(it) }
    )
    val schedule: StateFlow<List<ScheduleDay>> = _schedule

    private val _registrationError = MutableStateFlow<String?>(null)
    val registrationError: StateFlow<String?> = _registrationError

    private val apiService = ApiService.create()

    fun onWeightChange(newWeight: String) {
        _weight.value = newWeight
    }

    fun onHeightChange(newHeight: String) {
        _height.value = newHeight
    }

    fun onScheduleChange(newSchedule: List<ScheduleDay>) {
        _schedule.value = newSchedule
    }

    fun saveProfileAndSchedule(onRegistrationComplete: () -> Unit) {
        viewModelScope.launch {
            val scheduleRequest = schedule.first().map {
                ScheduleDayRequest(it.dayName, it.workout?.name)
            }
            val userProfile = UserProfileRequest(
                weight = weight.first(),
                height = height.first(),
                schedule = scheduleRequest
            )
            try {
                apiService.saveUserProfile(userProfile)
                onRegistrationComplete()
            } catch (e: Exception) {
                _registrationError.value = "Registracija nepavyko: ${e.message}"
            }
        }
    }
}
