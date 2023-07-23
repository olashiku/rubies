package com.qucoon.rubiesnigeria.model.response.addfriend

data class AddFriendResponse(
    val action: String,
    val message: String,
    val responseCode: String,
    val responseMessage: String,
    val sender: String
)