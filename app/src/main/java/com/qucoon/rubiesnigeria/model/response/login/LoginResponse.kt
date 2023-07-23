package com.qucoon.rubiesnigeria.model.response.login

data class LoginResponse(
    val action: String,
    val responseCode: String,
    val responseMessage: String,
    val token: String
)