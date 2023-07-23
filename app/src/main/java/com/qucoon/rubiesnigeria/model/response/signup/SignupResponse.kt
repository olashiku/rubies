package com.qucoon.rubiesnigeria.model.response.signup

data class SignupResponse(
    val action: String,
    val responseCode: String,
    val responseMessage: String,
    val token: String?
)