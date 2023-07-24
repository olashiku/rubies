package com.qucoon.rubiesnigeria.model.request.addfriend


@kotlinx.serialization.Serializable
data class AddFriendRequest(
    val action: String,
    val friends: List<Friend>,
    val userPhone: String
)