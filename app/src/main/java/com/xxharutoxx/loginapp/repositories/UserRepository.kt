package com.xxharutoxx.loginapp.repositories

import com.google.gson.Gson
import com.xxharutoxx.loginapp.model.api.UserService
import com.xxharutoxx.loginapp.model.request.LoginRequest
import com.xxharutoxx.loginapp.model.request.RegisterRequest
import com.xxharutoxx.loginapp.model.response.ExceptionResponse
import com.xxharutoxx.loginapp.model.response.TokenResponse
import com.xxharutoxx.loginapp.networking.ApiClient
import retrofit2.HttpException

class UserRepository {

    suspend fun loginUser(
        request: LoginRequest,
    ): Result<TokenResponse> {
        val apiClient = ApiClient.instance.create(UserService::class.java)
        return try {
            val response = apiClient.loginUser(request)
            Result.success(response)
        }catch (e: HttpException){
            val errorBody = e.response()?.errorBody()?.string()
            val errorResponse = Gson().fromJson(errorBody, ExceptionResponse::class.java)
            Result.failure(Exception(errorResponse.message))
        }catch (e:Exception){
            Result.failure(e)
        }
    }

    suspend fun registerUser(
        request: RegisterRequest
    ): TokenResponse {
        val apiClient = ApiClient.instance.create(UserService::class.java)
        return apiClient.registerUser(request)
    }
}