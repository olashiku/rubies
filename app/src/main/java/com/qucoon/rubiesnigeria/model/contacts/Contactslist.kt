package com.qucoon.rubiesnigeria.model.contacts

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Contactslist")
data class Contactslist(
    @PrimaryKey(autoGenerate = true)
    val id:Int = 0,
    val name: String,
    val phonenumber: String,
    val isFriend:String
):java.io.Serializable