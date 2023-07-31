package com.qucoon.rubiesnigeria.model.request.login

@kotlinx.serialization.Serializable
data class LoginRequest(
    val phone: String
)