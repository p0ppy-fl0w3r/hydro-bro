package com.fl0w3r.network

import com.fl0w3r.model.LoginModel
import com.fl0w3r.model.RegisterModel
import com.fl0w3r.model.UserResponse
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.delay
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

private const val BASE_URL = "https://hydro.azurewebsites.net/"

private val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()

private val retrofit =
    Retrofit.Builder().addConverterFactory(MoshiConverterFactory.create(moshi)).baseUrl(BASE_URL)
        .build()

interface HydroApiService {
    @POST("api/Auth/login")
    suspend fun loginUser(@Body loginModel: LoginModel): UserResponse

    @POST("api/Auth/register")
    suspend fun registerUser(@Body loginModel: RegisterModel)

    @GET("api/Auth/verify")
    suspend fun isTokenValid(@Header("Authorization") token: String): Boolean

}

object HydroApi {
    val hydroApiService: HydroApiService by lazy {
        retrofit.create(HydroApiService::class.java)
    }
}