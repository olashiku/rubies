package com.qucoon.rubiesnigeria.network

import com.qucoon.rubiesnigeria.model.request.login.LoginRequest
import com.qucoon.rubiesnigeria.model.request.signup.SignupRequest
import com.qucoon.rubiesnigeria.model.response.login.LoginResponse
import com.qucoon.rubiesnigeria.model.response.signup.SignupResponse
import kotlinx.coroutines.Deferred
import retrofit2.http.Body
import retrofit2.http.POST


interface RubiesApi {
    @POST("login")
    fun loginUser(@Body params: LoginRequest) : Deferred<LoginResponse>
    fun sigupUser(@Body params: SignupRequest) : Deferred<SignupResponse>


}