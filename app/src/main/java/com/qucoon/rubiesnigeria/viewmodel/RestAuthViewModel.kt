package com.qucoon.rubiesnigeria.viewmodel

import com.qucoon.rubiesnigeria.base.BaseViewModel
import com.qucoon.rubiesnigeria.model.request.login.LoginRequest
import com.qucoon.rubiesnigeria.model.request.signup.SignupRequest
import com.qucoon.rubiesnigeria.model.response.login.LoginResponse
import com.qucoon.rubiesnigeria.model.response.signup.SignupResponse
import com.qucoon.rubiesnigeria.network.SingleLiveEvent
import com.qucoon.rubiesnigeria.repository.socket.AuthRepository

class RestAuthViewModel(val authRepository: AuthRepository):BaseViewModel() {

    val loginResponseNew = SingleLiveEvent<LoginResponse>()
    val signupResponse = SingleLiveEvent<SignupResponse>()


    fun loginResponseNew(phone: String) {
        val request =  LoginRequest(phone = phone)
        return apiRequest(request, authRepository::loginUser, loginResponseNew, { it.responseMessage ?: "" })
    }

    fun signupResponse(phone: String, name: String) {
        val request =  SignupRequest(phone = phone, name = name)
        return apiRequest(request, authRepository::signUpUser, signupResponse, { it.responseMessage ?: "" })
    }
}