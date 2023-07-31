package com.qucoon.rubiesnigeria.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.qucoon.rubiesnigeria.base.BaseScarletSocketViewModel
import com.qucoon.rubiesnigeria.model.chat.Chat
import com.qucoon.rubiesnigeria.model.request.fetchfriends.FetchFriendsRequest
import com.qucoon.rubiesnigeria.model.request.login.LoginRequest
import com.qucoon.rubiesnigeria.model.request.privatetext.PrivateTextRequest
import com.qucoon.rubiesnigeria.model.request.signup.SignupRequest
import com.qucoon.rubiesnigeria.model.response.login.LoginResponse
import com.qucoon.rubiesnigeria.network.EndPoints
import com.qucoon.rubiesnigeria.network.SingleLiveEvent
import com.qucoon.rubiesnigeria.repository.socket.ScarletSocketRepository
import com.qucoon.rubiesnigeria.storage.PaperPrefs
import com.qucoon.rubiesnigeria.storage.getStringPref
import com.qucoon.rubiesnigeria.storage.room.data_source.ChatsDataSource
import com.qucoon.rubiesnigeria.utils.convertRequest
import com.qucoon.rubiesnigeria.utils.getObject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SocketAuthenticationViewModel(
    val scarletSocketRepository: ScarletSocketRepository, val chatsDataSource: ChatsDataSource) : BaseScarletSocketViewModel() {

    val loginResponse = SingleLiveEvent<LoginResponse>()


    fun getFriends(){
        val request = FetchFriendsRequest(EndPoints.FETCH_FRIEND_ACTION)
        println("requesting $request")
        scarletSocketRepository.sendMessage( convertRequest(request))
    }

    fun getChatByRecipient(recipient:String): LiveData<List<Chat>> {
        return chatsDataSource.getChatByRecipient(recipient)
    }


    fun sendMessages(message: String, receiver: String,sender:String) {
        val authenticationProcess = paperPrefs.getStringPref(PaperPrefs.USER_PHONE)+receiver
        viewModelScope.launch(Dispatchers.IO) {
            chatsDataSource.updateChat(Chat(0,sender,receiver,message,authenticationProcess))
        }
        val request = PrivateTextRequest(EndPoints.PRIVATE_TEXT_ACTION, message, receiver)
        scarletSocketRepository.sendMessage(convertRequest(request))
    }


}