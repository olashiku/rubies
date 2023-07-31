package com.qucoon.rubiesnigeria.model.response.login

data class LoginResponse(
    val token: String,
    val userActive: Boolean,
    val userCreateDate: String,
    val userId: String,
    val userName: String,
    val userStatus: String,
    val userUpdateDate: String,
    val responseCode:String?,
    val responseMessage:String?
)