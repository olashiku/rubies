package com.qucoon.rubiesnigeria.model.request.signup

@kotlinx.serialization.Serializable
data class SignupRequest(
    val name: String,
    val phone: String
)