package com.xxharutoxx.loginapp.viewmodels

import retrofit2.HttpException
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import com.xxharutoxx.loginapp.model.request.LoginRequest
import com.xxharutoxx.loginapp.model.request.RegisterRequest
import com.xxharutoxx.loginapp.model.response.ExceptionResponse
import com.xxharutoxx.loginapp.model.response.TokenResponse
import com.xxharutoxx.loginapp.repositories.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class UserViewModel:ViewModel() {

    private val userRepository = UserRepository()

    private val _registerResponse: MutableStateFlow<TokenResponse> = MutableStateFlow(TokenResponse(token = ""))
    var registerResponse: StateFlow<TokenResponse> = _registerResponse

    private val _errorResponse = MutableStateFlow<ExceptionResponse?>(null)
    val errorResponse: StateFlow<ExceptionResponse?> = _errorResponse.asStateFlow()

    private val _loginResponse = MutableStateFlow<TokenResponse?>(null)
    val loginResponse: StateFlow<TokenResponse?> = _loginResponse
    private val _isLoadingLogin = MutableStateFlow(false)
    val isLoadingLogin: StateFlow<Boolean> = _isLoadingLogin.asStateFlow()

    private val _isLoadingRegister = MutableStateFlow(false)
    val isLoadingRegister: StateFlow<Boolean> = _isLoadingRegister.asStateFlow()


    fun loginUser(request: LoginRequest) {
        viewModelScope.launch(Dispatchers.IO) {
            _isLoadingLogin.value = true
            val result = userRepository.loginUser(request)
            if (result.isSuccess) {
                _loginResponse.value = result.getOrNull()
            } else {
                val exceptionMessage = result.exceptionOrNull()?.message
                _errorResponse.value = ExceptionResponse(exceptionMessage ?: "Unknown error", "401")
            }
            _isLoadingLogin.value = false
        }
    }

    fun registerUser(request: RegisterRequest) {
        viewModelScope.launch {
            try {
                _isLoadingRegister.value = true
                val userResponse = userRepository.registerUser(request)
                _registerResponse.value = userResponse
            } catch (e: HttpException) {
                val errorBody = e.response()?.errorBody()?.string()
                val errorResponse = Gson().fromJson(errorBody, ExceptionResponse::class.java)
                _errorResponse.value = errorResponse
            } catch (e: Exception) {
                _errorResponse.value = ExceptionResponse(e.message ?: "Unknown error", "401")
            } finally {
                _isLoadingRegister.value = false
            }
        }
    }

}