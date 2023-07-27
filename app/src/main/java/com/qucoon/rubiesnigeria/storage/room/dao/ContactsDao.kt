package com.qucoon.rubiesnigeria.storage.room.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import com.qucoon.rubiesnigeria.model.contacts.Contactslist

@Dao
interface  ContactsDao:BaseDao<Contactslist>{

    @Query("select * from Contactslist order by isFriend  desc")
    fun getAllContacts(): LiveData<List<Contactslist>>

    @Query("DELETE FROM Contactslist")
    fun deleteAllContacts()


    @Query("update Contactslist set isFriend=:status where id=:id")
    fun updateFriendStatus(status:String,id:Int)

    @Transaction
    fun updateContacts(contacts: List<Contactslist>) {
        deleteAllContacts()
        insertAll(contacts)
    }

     fun updateContact(contactList: Contactslist){
         insert(contactList)
     }
}