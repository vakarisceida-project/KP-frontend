package com.example.pirmas.data

data class UserProfileRequest(
    val weight: String,
    val height: String,
    val schedule: List<ScheduleDayRequest>
)

data class ScheduleDayRequest(
    val dayName: String,
    val workoutName: String?
)

data class LoginRequest(
    val username: String,
    val password: String
)

data class LoginResponse(
    val success: Boolean,
    val message: String
)

data class ScheduleResponse(
    val schedule: List<WorkoutResponse>
)

data class WorkoutResponse(
    val day: String,
    val workout: String
)
