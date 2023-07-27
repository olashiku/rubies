package com.qucoon.rubiesnigeria.model.request.privatetext

@kotlinx.serialization.Serializable
data class PrivateTextRequest(
    val action: String,
    val message: String,
    val user: String
)