package com.qucoon.rubiesnigeria.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.qucoon.rubiesnigeria.base.BaseSocketViewModel
import com.qucoon.rubiesnigeria.model.chat.Chat
import com.qucoon.rubiesnigeria.model.contacts.Contactslist
import com.qucoon.rubiesnigeria.model.request.addfriend.AddFriendRequest
import com.qucoon.rubiesnigeria.model.request.addfriend.Friend
import com.qucoon.rubiesnigeria.model.request.privatetext.PrivateTextRequest
import com.qucoon.rubiesnigeria.network.EndPoints
import com.qucoon.rubiesnigeria.repository.socket.SocketRepository
import com.qucoon.rubiesnigeria.storage.PaperPrefs
import com.qucoon.rubiesnigeria.storage.getStringPref
import com.qucoon.rubiesnigeria.storage.room.data_source.ChatsDataSource
import com.qucoon.rubiesnigeria.storage.room.data_source.ContactsDataSource
import com.qucoon.rubiesnigeria.utils.cleanString
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ChatViewModel(
    val dataSource: ContactsDataSource,
    val socketRepository: SocketRepository,
    val chatsDataSource: ChatsDataSource
) : BaseSocketViewModel() {

    fun getContacts(): LiveData<List<Contactslist>> {
        return dataSource.getContacts()
    }

     fun getChatByRecipient(recipient:String): LiveData<List<Chat>> {
        return chatsDataSource.getChatByRecipient(recipient)
     }

    fun addFriends(contactList: Contactslist) {
        showLoading.value = true
        val friend = listOf(Friend(contactList.name, contactList.phonenumber))
        val friendRequest = AddFriendRequest(
            EndPoints.ADD_FRIEND_ACTION,
            friend,
            paperPrefs.getStringPref(PaperPrefs.USER_PHONE)
        )
        viewModelScope.launch(Dispatchers.IO) {
            dataSource.updateFriendStatus("yes",contactList.id)
        }
        sendMessage(friendRequest, socketRepository::sendMessage)
    }


    fun sendMessages(message: String, receiver: String,sender:String) {
        val authenticationProcess = paperPrefs.getStringPref(PaperPrefs.USER_PHONE)+receiver
        viewModelScope.launch(Dispatchers.IO) {
            chatsDataSource.updateChat(Chat(0,sender,receiver,message,authenticationProcess))
        }
        val request = PrivateTextRequest(EndPoints.PRIVATE_TEXT_ACTION, message, receiver)
        sendMessage(request, socketRepository::sendMessage)
    }


}