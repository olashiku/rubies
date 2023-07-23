package com.qucoon.rubiesnigeria.storage.room.engine

import androidx.room.Database
import androidx.room.RoomDatabase
import com.qucoon.rubiesnigeria.model.contacts.Contactslist
import com.qucoon.rubiesnigeria.storage.room.dao.ContactsDao


@Database(entities =[Contactslist::class],version =1,exportSchema = false)
abstract  class DatabaseEngine: RoomDatabase(){
    abstract  fun contactsDao(): ContactsDao
}