package com.example.pirmas.data

data class FullRegistrationRequest(
    val username: String,
    val password: String,
    val weight: String,
    val height: String,
    val schedule: List<ScheduleDayRequest>
)

data class UpdateProfileRequest(
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
    val message: String,
    val token: String? // Pridėtas token
)

data class ScheduleResponse(
    val schedule: List<WorkoutResponse>
)

data class WorkoutResponse(
    val day: String,
    val workout: String
)

data class ProfileResponse(
    val username: String,
    val weight: String,
    val height: String,
    val schedule: List<WorkoutResponse> // Pridėtas tvarkaraštis
)
