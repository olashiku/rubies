package com.qucoon.rubiesnigeria.viewmodel

import androidx.lifecycle.viewModelScope
import com.qucoon.rubiesnigeria.base.BaseScarletSocketViewModel
import com.qucoon.rubiesnigeria.model.response.login.LoginResponse
import com.qucoon.rubiesnigeria.model.response.signup.SignupResponse
import com.qucoon.rubiesnigeria.network.EndPoints
import com.qucoon.rubiesnigeria.network.SingleLiveEvent
import com.qucoon.rubiesnigeria.repository.socket.ScarletSocketRepository
import com.qucoon.rubiesnigeria.utils.getObject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ScarletResponseViewModel(val scarletSocketRepository: ScarletSocketRepository) :
    BaseScarletSocketViewModel() {

    val loginResponse = SingleLiveEvent<LoginResponse>()
    val registerResponse = SingleLiveEvent<SignupResponse>()




    fun getAndAssignResponse() {
        viewModelScope.launch(Dispatchers.IO) {
            scarletSocketRepository.observeMessage().collect { response ->
                println("expecting_response $response")
                when (getSocketAction(response)) {
                    EndPoints.LOGIN_ACTION -> {
                        performLoginOperation(response.getObject())
                    }

                    EndPoints.SIGNUP_ACTION -> {}
                    EndPoints.FETCH_FRIEND_ACTION -> {
                        println("value_of_friends $response")
                    }

                    else -> {
                        println("response_from_socket $response ")
                    }
                }
            }
        }
    }

     fun performLoginOperation(response: LoginResponse){
         loginResponse.postValue(response)
     }


}