package com.qucoon.rubiesnigeria.storage.room.data_source

import androidx.lifecycle.LiveData
import com.qucoon.rubiesnigeria.model.contacts.Contactslist
import com.qucoon.rubiesnigeria.storage.room.dao.ContactsDao

interface ContactsDataSource {
    fun getContacts():LiveData<List<Contactslist>>
    fun deleteAllContacts()
    fun updateContacts(contact:  List<Contactslist>)
    fun updateContact(contact:Contactslist)
    fun updateFriendStatus(status:String,id:Int)
}
 class ContactsDataSourceImpl(val contactsDao: ContactsDao):ContactsDataSource{
     override fun getContacts(): LiveData<List<Contactslist>> {
      return   contactsDao.getAllContacts()
     }

     override fun deleteAllContacts() {
         contactsDao.deleteAllContacts()
     }

     override fun updateContacts(contact: List<Contactslist>) {
         contactsDao.updateContacts(contact)
     }

     override fun updateContact(contact: Contactslist) {
         println("i_have_been_called $contact")
         contactsDao.updateContact(contact)
     }

     override  fun updateFriendStatus(status:String,id:Int){
         contactsDao.updateFriendStatus(status,id)
     }

 }