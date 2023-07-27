package com.qucoon.rubiesnigeria.network

import com.qucoon.rubiesnigeria.utils.Constant
import com.qucoon.rubiesnigeria.utils.Utils
import io.ktor.client.call.*
import io.ktor.client.features.*
import io.ktor.client.features.websocket.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.http.cio.websocket.*
import kotlinx.coroutines.cancelAndJoin
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import java.nio.channels.UnresolvedAddressException


interface ChatSocketService {
    suspend fun <T : Any> openSocketSession(): NetworkResult<T>
    suspend fun <T:Any> sendSocketMessage(message: String)
    suspend fun closeSocketSession()
}
open class BaseSocketRepository(val networkProvider: NetworkProvider) : ChatSocketService {

    private var socket: WebSocketSession? = null

    override suspend  fun <T:Any> openSocketSession(): NetworkResult<T> {
        return try {
                   socket = networkProvider.socketClient.webSocketSession {
                       url(EndPoints.BASE_SOCKET_HOST)
                   }
            if(socket?.isActive == true) {
                NetworkResult.SuccessSocketConnection("success")

            } else    NetworkResult.Failed("Couldn't establish a connection.")

        }catch (ex: ClientRequestException){
            ex.printStackTrace()
            NetworkResult.Errror(ex)
        }catch (ex: UnresolvedAddressException){
            ex.printStackTrace()
            NetworkResult.Errror(ex)
            NetworkResult.Failed("Seems you don't have an internet connection!")

        }
    }

    override suspend fun <T:Any> sendSocketMessage(message: String) {
        try {
            socket?.send(Frame.Text(message))
        }catch (ex:Exception){
            ex.printStackTrace()
        }
    }


    fun  observeSocketMessage(): Flow<String> {
        var myFlow = flowOf<String>()
        return  try {
                myFlow =   socket?.incoming?.receiveAsFlow()?.filter { it is Frame.Text }?.map {
                    val string = (it as Frame.Text).readText()?: ""
                    string
                } ?: flow {  }

            myFlow
        }catch (ex:Exception){
            ex.printStackTrace()
            flow {  }
        }
    }
    override suspend fun closeSocketSession() {
        socket?.close()
    }
}