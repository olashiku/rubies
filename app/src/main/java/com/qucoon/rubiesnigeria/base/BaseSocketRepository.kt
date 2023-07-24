package com.qucoon.rubiesnigeria.network

import com.qucoon.rubiesnigeria.storage.PaperPrefs
import com.qucoon.rubiesnigeria.utils.Constant
import io.ktor.client.call.*
import io.ktor.client.features.websocket.*
import io.ktor.client.request.url
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.http.cio.websocket.*
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.isActive
import org.koin.java.KoinJavaComponent.inject


interface ChatSocketService {
    suspend fun <T : Any> openSocketSession(): NetworkResult<T>
    suspend fun sendSocketMessage(message: String)
    suspend fun closeSocketSession()
}

open class BaseSocketRepository(val networkProvider: NetworkProvider) : ChatSocketService {

    var socket: WebSocketSession? = null

    override suspend fun <T : Any> openSocketSession(): NetworkResult<T> {
        return try {
            println("active_socket_status "+socket?.isActive)
            socket = if(socket?.isActive == true){
                networkProvider.socketClient.webSocketSession {
                    url(EndPoints.BASE_SOCKET_HOST)
                }
            }else{
                networkProvider.socketClient.webSocketSession {
                    url(EndPoints.BASE_SOCKET_HOST)
                }
            }
            NetworkResult.SuccessSocketConnection(Constant.success)
        } catch (ex: Exception) {
            if (ex.message.toString().contains(Constant.userAlreadyRegisteredMessage)) {
                NetworkResult.SuccessSocketConnection(Constant.success)
            } else {
                NetworkResult.Errror(ex)
            }
        }
    }

    suspend fun observeMessageString(): String {
        var response = ""
        try {
            for (message in socket!!.incoming) {
                message as? Frame.Text ?: continue
                response = message.readText()
            }
        } catch (e: Exception) {
            response = ("Error while receiving: " + e.message)
        }
        return response
    }


    fun observeSocketMessage(): Flow<String> {
        return try {
            socket!!.incoming.receiveAsFlow().filter { it is Frame.Text }.map {
                val string = (it as Frame.Text).readText() ?: ""
                string
            }
        } catch (ex: Exception) {
            ex.printStackTrace()
            flow { }
        }
    }

    override suspend fun sendSocketMessage(message: String) {
        try {
            println("expecting_string_outgoing $message")
            socket?.send(Frame.Text(message))
        } catch (ex: Exception) {
            ex.printStackTrace()
        }
    }

    override suspend fun closeSocketSession() {
        socket?.close()
    }
}