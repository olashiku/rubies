package com.qucoon.rubiesnigeria.model.request.addfriend


data class AddFriendRequest(
    val action: String,
    val friends: List<Friend>,
    val userPhone: String
)