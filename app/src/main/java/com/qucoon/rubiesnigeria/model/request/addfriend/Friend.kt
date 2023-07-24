package com.qucoon.rubiesnigeria.model.request.addfriend

@kotlinx.serialization.Serializable
data class Friend(
    val name: String,
    val phoneId: String
)