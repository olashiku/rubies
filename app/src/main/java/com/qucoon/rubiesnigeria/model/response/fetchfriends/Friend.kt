package com.qucoon.rubiesnigeria.model.response.fetchfriends

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "friends")
data class Friend(
    val userActive: Boolean,
    val userConnectionId: String,
    val userCreateDate: String,
    @PrimaryKey val userId: String,
    val userName: String,
    val userStatus: String,
    val userUpdateDate: String
): Serializable