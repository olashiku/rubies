package com.qucoon.rubiesnigeria.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import com.qucoon.rubiesnigeria.base.BaseSocketViewModel
import com.qucoon.rubiesnigeria.model.request.login.LoginRequest
import com.qucoon.rubiesnigeria.model.request.signup.SignupRequest
import com.qucoon.rubiesnigeria.model.response.login.LoginResponse
import com.qucoon.rubiesnigeria.model.response.signup.SignupResponse
import com.qucoon.rubiesnigeria.network.EndPoints
import com.qucoon.rubiesnigeria.network.SingleLiveEvent
import com.qucoon.rubiesnigeria.repository.socket.SocketRepository
import com.qucoon.rubiesnigeria.storage.PaperPrefs
import com.qucoon.rubiesnigeria.storage.savePref
import com.qucoon.rubiesnigeria.utils.Constant
import kotlinx.coroutines.launch

class AuthViewModel(val socketRepository: SocketRepository) : BaseSocketViewModel() {

    private val loadingEndpoint = setOf(EndPoints.LOGIN_ACTION, EndPoints.SIGNUP_ACTION)
    val loginResponse = SingleLiveEvent<LoginResponse>()
    val signupResponse = SingleLiveEvent<SignupResponse>()
    val showLoading = MutableLiveData<Boolean>()

    fun openConnection(){
        openSocketConnection(socketRepository::openSocket)
    }

    fun login(phoneNumber: String) {
        showLoading.value = true
        sendMessage(LoginRequest(EndPoints.LOGIN_ACTION, phoneNumber), socketRepository::sendMessage)
    }

     fun register(phoneNumber: String,firstName:String,lastName:String){
         showLoading.value = true
         val name = "$firstName $lastName"
         sendMessage(SignupRequest(EndPoints.SIGNUP_ACTION, name,phoneNumber), socketRepository::sendMessage)
     }

    fun observeResponse() {
        val result = observeSessionFlowResponse(socketRepository::observeRequest)
        viewModelScope.launch {
            result?.collect { data ->
                if (loadingEndpoint.contains(getSocketAction(data))) {
                    showLoading.value = false
                    performLoadingProcesses(data)
                } else {
                    performRegularProcesses(data)
                }
            }
        }
    }

    fun performLoadingProcesses(data: String) {

        when (getSocketAction(data)) {
            EndPoints.LOGIN_ACTION -> {
                loginOperations(data)
            }
            EndPoints.SIGNUP_ACTION -> {
                registerOperations(data)
            }
        }
    }

    fun performRegularProcesses(data: String) {

    }


    private fun loginOperations(data: String) {
        val response = Gson().fromJson(data,LoginResponse::class.java)
        if(response.responseCode.equals(Constant.success_code)){
            loginResponse.value = response
            paperPrefs.savePref(PaperPrefs.AUTHORIZATION_TOKEN, response.token)
        }else {
            errorMessage.value = response.responseMessage
        }
    }

    private fun registerOperations(data: String) {
        val response = Gson().fromJson(data,SignupResponse::class.java)
        signupResponse.value = response
        if(response.responseCode.equals(Constant.success_code)){
            signupResponse.value = response
        }else {
            errorMessage.value = response.responseMessage
        }
    }

    fun closeConnection() {
        viewModelScope.launch { socketRepository.closeSession() }
    }


}