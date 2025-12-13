package com.example.pirmas

import com.example.pirmas.data.LoginRequest
import com.example.pirmas.data.LoginResponse
import com.example.pirmas.data.ScheduleResponse
import com.example.pirmas.data.UserProfileRequest
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiService {
    @POST("profile")
    suspend fun saveUserProfile(@Body userProfile: UserProfileRequest)

    @POST("login")
    suspend fun login(@Body loginRequest: LoginRequest): Response<LoginResponse>

    @GET("schedule")
    suspend fun getSchedule(): Response<ScheduleResponse>

    companion object {
        private const val BASE_URL = "http://10.0.2.2:3000/"

        fun create(): ApiService {
            val retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
            return retrofit.create(ApiService::class.java)
        }
    }
}
