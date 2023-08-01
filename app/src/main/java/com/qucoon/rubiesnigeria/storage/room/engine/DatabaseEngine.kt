package com.qucoon.rubiesnigeria.storage.room.engine

import androidx.room.Database
import androidx.room.RoomDatabase
import com.qucoon.rubiesnigeria.model.chat.Chat
import com.qucoon.rubiesnigeria.model.contacts.Contactslist
import com.qucoon.rubiesnigeria.model.response.fetchfriends.Friend
import com.qucoon.rubiesnigeria.storage.room.dao.ChatDao
import com.qucoon.rubiesnigeria.storage.room.dao.ContactsDao
import com.qucoon.rubiesnigeria.storage.room.dao.FriendsDao


@Database(entities =[Contactslist::class,Chat::class, Friend::class,],version =6,exportSchema = false)
abstract  class DatabaseEngine: RoomDatabase(){
    abstract  fun contactsDao(): ContactsDao
    abstract fun chatDao(): ChatDao
    abstract fun friendsDao() : FriendsDao

}