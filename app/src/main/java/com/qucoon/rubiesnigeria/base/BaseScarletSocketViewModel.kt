package com.qucoon.rubiesnigeria.base

import androidx.lifecycle.*
import com.qucoon.rubiesnigeria.network.NetworkResult
import com.qucoon.rubiesnigeria.network.SingleLiveEvent
import com.qucoon.rubiesnigeria.storage.PaperPrefs
import com.qucoon.rubiesnigeria.utils.Constant
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.json.JSONObject
import org.koin.java.KoinJavaComponent.inject


open class BaseScarletSocketViewModel : ViewModel() {

    val showLoading = MutableLiveData<Boolean>()
    var isOpenLiveData = MutableLiveData<String>()
    val errorMessage =  SingleLiveEvent<String>()
    val paperPrefs : PaperPrefs by inject(PaperPrefs::class.java)

    inline fun <reified R : Any, S : Any> sendMessage(
        request: R,
        crossinline makeRequest: suspend (string: S) -> Unit
    ) {
        val string = Json.encodeToString(request) as S
        viewModelScope.launch {
            makeRequest(string)
        }
    }

    fun openSocketConnection(openSocket: suspend () -> NetworkResult<Unit>) {
        viewModelScope.launch {
            val result = openSocket()
            when (result) {
                is NetworkResult.SuccessSocketConnection<*> -> {
                    isOpenLiveData.postValue(Constant.success)
                }
                is NetworkResult.Failed<*> -> {
                    isOpenLiveData.postValue(Constant.failed)
                }
                is NetworkResult.Errror -> {
                    isOpenLiveData.postValue(result.exception.localizedMessage)
                }
                else -> {}
            }
        }
    }

    fun observeSessionResponse(observeRequest: suspend () -> Flow<String>): LiveData<String>? {
        var result: LiveData<String>? = null
        viewModelScope.launch {
            result = observeRequest().asLiveData()
        }
        return result
    }

    fun observeSessionFlowResponse(observeRequest: suspend () -> Flow<String>): Flow<String>? {
        var result: Flow<String>? = null
        viewModelScope.launch {
            result = observeRequest()

        }
        return result
    }

    fun getSocketAction(data: String): String {
        var obj = JSONObject(data)
        return obj["action"] as String
    }
}