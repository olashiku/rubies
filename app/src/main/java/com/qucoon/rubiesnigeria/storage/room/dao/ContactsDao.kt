package com.qucoon.rubiesnigeria.storage.room.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import com.qucoon.rubiesnigeria.model.contacts.Contactslist

@Dao
interface  ContactsDao:BaseDao<Contactslist>{

    @Query("select * from Contactslist order by name  asc")
    fun getAllContacts(): LiveData<List<Contactslist>>

    @Query("DELETE FROM Contactslist")
    fun deleteAllContacts()

    @Transaction
    fun updateContacts(contacts: List<Contactslist>) {
        deleteAllContacts()
        insertAll(contacts)
    }
}