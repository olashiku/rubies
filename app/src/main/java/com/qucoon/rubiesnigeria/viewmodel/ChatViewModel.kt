package com.qucoon.rubiesnigeria.viewmodel

import androidx.lifecycle.LiveData
import com.qucoon.rubiesnigeria.base.BaseSocketViewModel
import com.qucoon.rubiesnigeria.model.contacts.Contactslist
import com.qucoon.rubiesnigeria.model.request.addfriend.AddFriendRequest
import com.qucoon.rubiesnigeria.model.request.addfriend.Friend
import com.qucoon.rubiesnigeria.network.EndPoints
import com.qucoon.rubiesnigeria.repository.socket.SocketRepository
import com.qucoon.rubiesnigeria.storage.PaperPrefs
import com.qucoon.rubiesnigeria.storage.getStringPref
import com.qucoon.rubiesnigeria.storage.room.data_source.ContactsDataSource

class ChatViewModel(val dataSource: ContactsDataSource, val socketRepository:SocketRepository):BaseSocketViewModel() {

    fun getContacts(): LiveData<List<Contactslist>> {
       return dataSource.getContacts()
    }

    fun addFriends(contactList: Contactslist){
        showLoading.value = true
        val friend = listOf(Friend(contactList.name,contactList.phonenumber))
        val friendRequest = AddFriendRequest(EndPoints.ADD_FRIEND_ACTION,friend,paperPrefs.getStringPref(PaperPrefs.USER_PHONE))
        sendMessage(friendRequest, socketRepository::sendMessage)
    }



}