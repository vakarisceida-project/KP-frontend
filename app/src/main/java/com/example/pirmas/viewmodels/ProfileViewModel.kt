package com.example.pirmas.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pirmas.ApiService
import com.example.pirmas.R
import com.example.pirmas.data.ScheduleDayRequest
import com.example.pirmas.data.UpdateProfileRequest
import com.example.pirmas.ui.screens.ScheduleDay
import com.example.pirmas.ui.screens.Workout
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ProfileViewModel : ViewModel() {

    private val _username = MutableStateFlow("")
    val username: StateFlow<String> = _username.asStateFlow()

    private val _weight = MutableStateFlow("")
    val weight: StateFlow<String> = _weight.asStateFlow()

    private val _height = MutableStateFlow("")
    val height: StateFlow<String> = _height.asStateFlow()

    private val _schedule = MutableStateFlow<List<ScheduleDay>>(emptyList())
    val schedule: StateFlow<List<ScheduleDay>> = _schedule.asStateFlow()

    private val _updateStatus = MutableStateFlow<String?>(null)
    val updateStatus: StateFlow<String?> = _updateStatus.asStateFlow()

    private val apiService = ApiService.create()

    private val availableWorkouts = listOf(
        Workout(R.drawable.legs, "Kojos"),
        Workout(R.drawable.push, "Stumimas"),
        Workout(R.drawable.pull, "Traukimas"),
        Workout(R.drawable.poilsis, "Poilsis"),
    )

    init {
        getProfile()
    }

    private fun getProfile() {
        viewModelScope.launch {
            try {
                val response = apiService.getProfile()
                if (response.isSuccessful) {
                    response.body()?.let {
                        _username.value = it.username
                        _weight.value = it.weight
                        _height.value = it.height

                        val dayNames = listOf("P", "A", "T", "K", "Pn", "Š", "S")
                        val newSchedule = dayNames.map {
                            dayName ->
                            val workoutForDay = it.schedule.find { it.day == dayName }?.workout
                            val workoutObject = availableWorkouts.find { it.name == workoutForDay }
                            ScheduleDay(dayName, workoutObject)
                        }
                        _schedule.value = newSchedule
                    }
                }
            } catch (e: Exception) {
                _updateStatus.value = "Nepavyko gauti profilio: ${e.message}"
            }
        }
    }

    fun onWeightChange(newWeight: String) {
        _weight.value = newWeight
    }

    fun onHeightChange(newHeight: String) {
        _height.value = newHeight
    }

    fun onScheduleChange(newSchedule: List<ScheduleDay>) {
        _schedule.value = newSchedule
    }

    fun saveChanges() {
        viewModelScope.launch {
            val scheduleRequest = _schedule.value.map {
                ScheduleDayRequest(it.dayName, it.workout?.name)
            }
            val updateRequest = UpdateProfileRequest(
                weight = _weight.value,
                height = _height.value,
                schedule = scheduleRequest
            )
            try {
                val response = apiService.updateProfile(updateRequest)
                if(response.isSuccessful) {
                    _updateStatus.value = "Pakeitimai išsaugoti sėkmingai!"
                } else {
                    _updateStatus.value = "Klaida išsaugant: ${response.message()}"
                }
            } catch (e: Exception) {
                _updateStatus.value = "Klaida: ${e.message}"
            }
        }
    }

    fun logout(onLogout: () -> Unit) {
        onLogout()
    }
}
