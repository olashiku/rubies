package com.qucoon.rubiesnigeria.model.response.fetchfriends

data class Friend(
    val userActive: Boolean,
    val userConnectionId: String,
    val userCreateDate: String,
    val userId: String,
    val userName: String,
    val userStatus: String,
    val userUpdateDate: String
)