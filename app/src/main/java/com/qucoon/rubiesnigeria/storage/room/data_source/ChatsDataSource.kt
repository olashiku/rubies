package com.qucoon.rubiesnigeria.storage.room.data_source

import androidx.lifecycle.LiveData
import com.qucoon.rubiesnigeria.model.chat.Chat
import com.qucoon.rubiesnigeria.storage.room.dao.ChatDao

interface ChatsDataSource {
    fun getAllChats(): LiveData<List<Chat>>
    fun getChatByRecipient(recipient:String): LiveData<List<Chat>>
    fun updateChats(chat: List<Chat>)
    fun updateChat(chat: Chat)
}

 class ChatsDataSourceImpl(val chatDao: ChatDao):ChatsDataSource{

     override fun getAllChats(): LiveData<List<Chat>> {
       return  chatDao.getAllChats()
     }

     override fun updateChats(chat: List<Chat>) {
        chatDao.updateChats(chat)
     }

     override fun updateChat(chat: Chat) {
         chatDao.updateChat(chat)
     }

     override fun getChatByRecipient(recipient: String): LiveData<List<Chat>> {
       return  chatDao.getAllChatByRecipient(recipient)
     }

 }