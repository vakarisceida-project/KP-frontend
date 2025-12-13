package com.example.pirmas.api

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

data class RegistrationRequest(
    val email: String,
    val password: String,
    val firstName: String,
    val lastName: String
)

data class User(
    val id: String,
    val email: String,
    val firstName: String,
    val lastName: String,
    val createdAt: String,
    val updatedAt: String
)

data class RegistrationResponse(
    val message: String,
    val user: User
)

interface ApiService {
    @POST("/auth/register")
    fun registerUser(@Body request: RegistrationRequest): Call<RegistrationResponse>
}
