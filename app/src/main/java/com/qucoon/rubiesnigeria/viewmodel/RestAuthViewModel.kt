package com.qucoon.rubiesnigeria.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import com.qucoon.rubiesnigeria.base.BaseViewModel
import com.qucoon.rubiesnigeria.model.request.login.LoginRequest
import com.qucoon.rubiesnigeria.model.request.signup.SignupRequest
import com.qucoon.rubiesnigeria.model.response.fetchfriends.Friend
import com.qucoon.rubiesnigeria.model.response.login.LoginResponse
import com.qucoon.rubiesnigeria.model.response.signup.SignupResponse
import com.qucoon.rubiesnigeria.network.SingleLiveEvent
import com.qucoon.rubiesnigeria.repository.socket.AuthRepository
import com.qucoon.rubiesnigeria.storage.room.data_source.FriendsDataSource

class RestAuthViewModel(val authRepository: AuthRepository, private val friendsDataSource: FriendsDataSource):BaseViewModel() {

    val loginResponseNew = SingleLiveEvent<LoginResponse>()
    val signupResponse = SingleLiveEvent<SignupResponse>()


    fun loginResponseNew(phone: String) {
        val request =  LoginRequest(phone = phone)
        return apiRequest(request, authRepository::loginUser, loginResponseNew, { it.responseMessage ?: "" })
    }

    fun signupResponse(phone: String, name: String) {
        val request =  SignupRequest(phone = phone, name = name)
        return apiRequest(request, authRepository::signUpUser, signupResponse, { it.responseMessage ?: "" })
    }

    fun getFriendsDB(): LiveData<List<Friend>> {
        return friendsDataSource.getAllFriends().asLiveData()
    }
}