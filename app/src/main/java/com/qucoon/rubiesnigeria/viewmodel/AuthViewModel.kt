package com.qucoon.rubiesnigeria.viewmodel

import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import com.qucoon.rubiesnigeria.base.BaseSocketViewModel
import com.qucoon.rubiesnigeria.model.chat.Chat
import com.qucoon.rubiesnigeria.model.contacts.Contactslist
import com.qucoon.rubiesnigeria.model.request.fetchfriends.FetchFriendsRequest
import com.qucoon.rubiesnigeria.model.request.login.LoginRequest
import com.qucoon.rubiesnigeria.model.request.privatetext.PrivateTextRequest
import com.qucoon.rubiesnigeria.model.request.signup.SignupRequest
import com.qucoon.rubiesnigeria.model.response.addfriend.AddFriendResponse
import com.qucoon.rubiesnigeria.model.response.fetchfriends.FetchFriendsResponse
import com.qucoon.rubiesnigeria.model.response.login.LoginResponse
import com.qucoon.rubiesnigeria.model.response.privatetext.PrivateTextResponse
import com.qucoon.rubiesnigeria.model.response.signup.SignupResponse
import com.qucoon.rubiesnigeria.network.EndPoints
import com.qucoon.rubiesnigeria.network.SingleLiveEvent
import com.qucoon.rubiesnigeria.repository.socket.SocketRepository
import com.qucoon.rubiesnigeria.storage.PaperPrefs
import com.qucoon.rubiesnigeria.storage.getStringPref
import com.qucoon.rubiesnigeria.storage.room.data_source.ChatsDataSource
import com.qucoon.rubiesnigeria.storage.room.data_source.ContactsDataSource
import com.qucoon.rubiesnigeria.storage.savePref
import com.qucoon.rubiesnigeria.utils.Constant
import com.qucoon.rubiesnigeria.utils.cleanString
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AuthViewModel(
    val socketRepository: SocketRepository,
    val contactsDataSource: ContactsDataSource,
    val chatsDataSource: ChatsDataSource
) : BaseSocketViewModel() {

    private val loadingEndpoint =
        setOf(EndPoints.LOGIN_ACTION, EndPoints.SIGNUP_ACTION, EndPoints.ADD_FRIEND_ACTION,EndPoints.FETCH_FRIEND_ACTION)
    val loginResponse = SingleLiveEvent<LoginResponse>()
    val signupResponse = SingleLiveEvent<SignupResponse>()
    val addFriendResponse = SingleLiveEvent<AddFriendResponse>()

    var userPhone = ""

    fun openConnection() {
        openSocketConnection(socketRepository::openSocket)
    }

    fun login(phoneNumber: String) {
        userPhone = phoneNumber
        val phone = phoneNumber.drop(1)
        showLoading.value = true
        sendMessage(
            LoginRequest(EndPoints.LOGIN_ACTION, "234$phone"),
            socketRepository::sendMessage
        )
    }

    fun fetchFriends() {
        showLoading.value = true
        sendMessage(
            FetchFriendsRequest(EndPoints.FETCH_FRIEND_ACTION),
            socketRepository::sendMessage
        )
    }

    fun register(phoneNumber: String, firstName: String, lastName: String) {
        showLoading.value = true
        val name = "$firstName $lastName"
        val phone = phoneNumber.drop(1)
        sendMessage(
            SignupRequest(EndPoints.SIGNUP_ACTION, name, "234$phone"),
            socketRepository::sendMessage
        )
    }

    fun observeResponse() {
        val result = observeSessionFlowResponse(socketRepository::observeRequest)
        viewModelScope.launch {
            result?.collect { data ->
                if (loadingEndpoint.contains(getSocketAction(data))) {
                    performLoadingProcesses(data)
                } else {
                    performRegularProcesses(data)
                }
            }
        }
    }

    fun performLoadingProcesses(data: String) {
        showLoading.value = false
        when (getSocketAction(data)) {
            EndPoints.LOGIN_ACTION -> {
                loginOperations(data)
            }
            EndPoints.SIGNUP_ACTION -> {
                registerOperations(data)
            }
            EndPoints.ADD_FRIEND_ACTION -> {
                addFriendsOperations(data)
            }
            EndPoints.FETCH_FRIEND_ACTION -> {
                fetchFriendsOperation(data)
            }
        }
    }

    fun addFriendsOperations(data: String) {
        val response = Gson().fromJson(data, AddFriendResponse::class.java)
        if (response.responseCode.equals(Constant.success_code)) {
            addFriendResponse.value = response
        } else {
            errorMessage.value = response.responseMessage
        }
    }

    fun performRegularProcesses(data: String) {
        when (getSocketAction(data)) {

            EndPoints.PRIVATE_TEXT_ACTION -> {
                privateTextActionOperation(data)
            }
        }
    }

    fun fetchFriendsOperation(data: String) {
        val response = Gson().fromJson(data, FetchFriendsResponse::class.java)
        viewModelScope.launch {
            response.friends.forEach {
                contactsDataSource.updateContact(
                    Contactslist(
                        0,
                        it.userName,
                        it.userId,
                        Constant.yes
                    )
                )
            }
        }
    }


    private fun loginOperations(data: String) {
        val response = Gson().fromJson(data, LoginResponse::class.java)
        if (response.responseCode.equals(Constant.success_code)) {
            loginResponse.value = response
            paperPrefs.savePref(PaperPrefs.USER_PHONE, userPhone)
            paperPrefs.savePref(PaperPrefs.AUTHORIZATION_TOKEN, response.token)
        } else {
            errorMessage.value = response.responseMessage
        }
    }

    private fun registerOperations(data: String) {
        val response = Gson().fromJson(data, SignupResponse::class.java)
        if (response.responseCode.equals(Constant.success_code)) {
            signupResponse.value = response
        } else {
            errorMessage.value = response.responseMessage
        }
    }


    fun closeConnection() {
        viewModelScope.launch { socketRepository.closeSession() }
    }


    fun privateTextActionOperation(data: String) {
        val response = Gson().fromJson(data, PrivateTextResponse::class.java)

        if (response.responseCode.equals(Constant.success_code)) {
            viewModelScope.launch(Dispatchers.IO) {
                val authentication =
                    paperPrefs.getStringPref(PaperPrefs.USER_PHONE) + response.sender
                chatsDataSource.updateChat(
                    Chat(
                        0,
                        response.sender.cleanString(),
                        paperPrefs.getStringPref(PaperPrefs.USER_PHONE),
                        response.message,
                        authentication
                    )
                )
            }
        } else {
            errorMessage.value = response.responseMessage
        }
    }


}