package com.qucoon.rubiesnigeria.model.request.signup

@kotlinx.serialization.Serializable
data class SignupRequest(
    val action: String,
    val name: String,
    val phone: String
)