package com.qucoon.rubiesnigeria.storage.room.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import com.qucoon.rubiesnigeria.model.chat.Chat
import com.qucoon.rubiesnigeria.model.contacts.Contactslist

@Dao
interface  ChatDao:BaseDao<Chat>{

    @Query("select * from Chat")
    fun getAllChats(): LiveData<List<Chat>>

    @Query("select * from Chat where senderReceiverToken like '%' || :sender || '%'")
    fun getAllChatByRecipient(sender:String): LiveData<List<Chat>>

    @Query("DELETE FROM Chat")
    fun deleteAllChat()

    @Transaction
    fun updateChats(chat: List<Chat>) {
        insertAll(chat)
    }

     fun updateChat(chat: Chat){
         insert(chat)
     }
}