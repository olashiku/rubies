package com.qucoon.rubiesnigeria.viewmodel

import androidx.lifecycle.LiveData
import com.qucoon.rubiesnigeria.base.BaseSocketViewModel
import com.qucoon.rubiesnigeria.model.contacts.Contactslist
import com.qucoon.rubiesnigeria.storage.room.data_source.ContactsDataSource

class ChatViewModel(val dataSource: ContactsDataSource):BaseSocketViewModel() {

    fun getContacts(): LiveData<List<Contactslist>> {
       return dataSource.getContacts()
    }
}