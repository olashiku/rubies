package com.qucoon.rubiesnigeria.storage.room.engine

import androidx.room.Database
import androidx.room.RoomDatabase
import com.qucoon.rubiesnigeria.model.chat.Chat
import com.qucoon.rubiesnigeria.model.contacts.Contactslist
import com.qucoon.rubiesnigeria.storage.room.dao.ChatDao
import com.qucoon.rubiesnigeria.storage.room.dao.ContactsDao


@Database(entities =[Contactslist::class,Chat::class],version =5,exportSchema = false)
abstract  class DatabaseEngine: RoomDatabase(){
    abstract  fun contactsDao(): ContactsDao
    abstract fun chatDao(): ChatDao
}