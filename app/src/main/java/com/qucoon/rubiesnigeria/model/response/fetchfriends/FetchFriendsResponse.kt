package com.qucoon.rubiesnigeria.model.response.fetchfriends

data class FetchFriendsResponse(
    val action: String,
    val friends: List<Friend>?,
    val responseCode: String,
    val responseMessage: String
)