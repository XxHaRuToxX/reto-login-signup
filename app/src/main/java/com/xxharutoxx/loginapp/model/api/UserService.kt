package com.xxharutoxx.loginapp.model.api

import com.xxharutoxx.loginapp.model.request.LoginRequest
import com.xxharutoxx.loginapp.model.request.RegisterRequest
import com.xxharutoxx.loginapp.model.response.TokenResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface UserService {
    @POST("auth/register")
    suspend fun registerUser(@Body dataBody: RegisterRequest):TokenResponse
    @POST("auth/login")
    suspend fun loginUser(@Body dataBody: LoginRequest):TokenResponse
}