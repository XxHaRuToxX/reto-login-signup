package com.xxharutoxx.loginapp.model.response

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class ExceptionResponse (
//    @SerializedName("message") var message: String,
//    @SerializedName("code") val code: String
    var message: String,
    val code: String
)