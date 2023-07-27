package com.qucoon.rubiesnigeria.model.chat

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Chat")
data class Chat(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val sender: String,
    val receiver:String,
    val message: String,
    val senderReceiverToken:String
)