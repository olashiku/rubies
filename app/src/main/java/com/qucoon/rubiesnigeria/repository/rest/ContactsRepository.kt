package com.qucoon.rubiesnigeria.repository.rest

import android.content.Context
import android.provider.ContactsContract
import com.qucoon.rubiesnigeria.model.contacts.Contactslist
import com.qucoon.rubiesnigeria.storage.room.data_source.ContactsDataSource
import java.util.ArrayList


interface ContactsRepository {
    suspend  fun getContacts(): List<Contactslist>
}

 class ContactsRepositoryImpl( private val appContext: Context,private val contactsDataSource: ContactsDataSource):ContactsRepository{
     override suspend fun getContacts(): List<Contactslist> {
         val contactList = ArrayList<Contactslist>()
         val cursor = appContext.contentResolver.query(ContactsContract.Contacts.CONTENT_URI, null, null, null, null)
         if (cursor!!.count > 0) {
             while (cursor.moveToNext()) {
                 val id = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID))
                 val phoneNumber = (cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER))).toInt()

                 if (phoneNumber > 0) {
                     val cursorPhone = appContext.contentResolver.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, ContactsContract.CommonDataKinds.Phone.CONTACT_ID + "=?", arrayOf(id), null)

                     if(cursorPhone!!.count > 0) {
                         while (cursorPhone.moveToNext()) {
                             val phoneNumValue = cursorPhone.getString(cursorPhone.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER))
                             val phoneName = cursorPhone.getString(cursorPhone.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME))
                             contactList.add(Contactslist(0,phoneName,phoneNumValue,"no"))
                         }
                     }
                     cursorPhone.close()
                 }
             }
             sendPhoneContactToDatabase(contactList)
         } else {
             contactsDataSource.updateContacts(contactList)
         }
         cursor.close()
         return contactList
     }

     private fun sendPhoneContactToDatabase(contactList: ArrayList<Contactslist>) {
       contactsDataSource.updateContacts(contactList)
     }

 }