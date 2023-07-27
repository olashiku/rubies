package com.qucoon.rubiesnigeria.model.response.privatetext

data class PrivateTextResponse(
    val action: String,
    val message: String,
    val responseCode: String,
    val responseMessage: String,
    val sender: String
)