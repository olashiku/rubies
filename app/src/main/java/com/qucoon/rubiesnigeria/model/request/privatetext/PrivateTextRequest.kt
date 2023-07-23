package com.qucoon.rubiesnigeria.model.request.privatetext

data class PrivateTextRequest(
    val action: String,
    val message: String,
    val user: String
)