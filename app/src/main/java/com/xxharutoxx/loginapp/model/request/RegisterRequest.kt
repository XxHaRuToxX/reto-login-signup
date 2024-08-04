package com.xxharutoxx.loginapp.model.request

import com.google.gson.annotations.SerializedName

data class RegisterRequest(
//    @SerializedName("username") var username: String,
//    @SerializedName("password") var password: String,
//    @SerializedName("confirmedPassword") var confirmedPassword: String,
//    @SerializedName("firstname") var firstname: String,
//    @SerializedName("lastname") var lastname: String,
//    @SerializedName("cellphone") var cellphone: String,
//    @SerializedName("country") var country: String

    var username: String,
    var password: String,
    var confirmedPassword: String,
    var firstname: String,
    var lastname: String,
    var cellphone: String,
    var country: String
)
