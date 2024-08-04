package com.xxharutoxx.loginapp.networking

import com.google.gson.GsonBuilder
import com.xxharutoxx.loginapp.constants.Constants
import com.xxharutoxx.loginapp.model.api.UserService
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

interface ApiClient {

//    private val logging = HttpLoggingInterceptor().apply {
//        setLevel(HttpLoggingInterceptor.Level.BODY)
//    }
//
//    private val client = OkHttpClient.Builder()
//        .addInterceptor(logging)
//        .build()
//
//    private val retrofit = Retrofit.Builder()
//        .baseUrl("https://backend-spring-431217.rj.r.appspot.com")
//        .addConverterFactory(GsonConverterFactory.create())
//        .client(client)
//        .build()
//
//    val userService: UserService = retrofit.create(UserService::class.java)

    companion object {
        val instance: Retrofit = Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(OkHttpClient.Builder().build())
            .build()
//            .create(UserService::class.java)
    }

}