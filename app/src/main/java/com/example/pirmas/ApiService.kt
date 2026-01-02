package com.example.pirmas

import com.example.pirmas.data.FullRegistrationRequest
import com.example.pirmas.data.LoginRequest
import com.example.pirmas.data.LoginResponse
import com.example.pirmas.data.ProfileResponse
import com.example.pirmas.data.UpdateProfileRequest
import okhttp3.OkHttpClient
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT

interface ApiService {
    @POST("register")
    suspend fun registerUser(@Body registrationRequest: FullRegistrationRequest): Response<Unit>

    @POST("login")
    suspend fun login(@Body loginRequest: LoginRequest): Response<LoginResponse>

    @GET("profile")
    suspend fun getProfile(): Response<ProfileResponse>

    @PUT("profile")
    suspend fun updateProfile(@Body updateProfileRequest: UpdateProfileRequest): Response<Unit>

    companion object {
        private const val BASE_URL = "https://gym-backend-dev.onrender.com/auth/"

        fun create(): ApiService {
            val client = OkHttpClient.Builder()
                .addInterceptor { chain ->
                    val originalRequest = chain.request()
                    val url = originalRequest.url().toString()

                    // Nepridėti "token", jei tai registracijos ar prisijungimo užklausa
                    if (url.endsWith("login") || url.endsWith("register")) {
                        chain.proceed(originalRequest)
                    } else {
                        val newRequest = originalRequest.newBuilder()
                        TokenManager.token?.let {
                            newRequest.addHeader("Authorization", "Bearer $it")
                        }
                        chain.proceed(newRequest.build())
                    }
                }
                .build()

            val retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client) // Pridedame savo sukurtą klientą
                .addConverterFactory(GsonConverterFactory.create())
                .build()

            return retrofit.create(ApiService::class.java)
        }
    }
}
