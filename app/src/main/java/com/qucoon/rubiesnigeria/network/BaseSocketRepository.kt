package com.qucoon.rubiesnigeria.network

import io.ktor.client.call.*
import io.ktor.client.features.websocket.*
import io.ktor.client.request.url
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.http.cio.websocket.*
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.isActive


interface ChatSocketService{
    suspend fun <T:Any> openSocketSession(): NetworkResult<T>
    suspend fun <T:Any> sendSocketMessage(message:T)
    suspend fun closeSocketSession()
}

open class BaseSocketRepository:ChatSocketService {
    val networkProvider = NetworkProvider()
    var  socket : WebSocketSession?  = null


    override suspend  fun <T:Any> openSocketSession(): NetworkResult<T> {
        return try {
            if(socket?.isActive != true){
                socket = networkProvider.socketClient.webSocketSession {
                    url("")
                }
                NetworkResult.SuccessSocketConnection("success")
            }else {
                socket = networkProvider.socketClient.webSocketSession {
                    url("")
                }
                NetworkResult.Failed("network error, something happened")
            }
        }catch (ex: Exception){
            NetworkResult.Errror(ex)
        }
    }

    suspend fun observeMessageString(): String {
        var   response = ""
        try {
            for (message in socket!!.incoming) {
                message as? Frame.Text ?: continue
                response =  message.readText()
            }
        } catch (e: Exception) {
            response  = ("Error while receiving: " + e.message)
        }
        return response
    }


    fun  observeSocketMessage(): Flow<String> {
        return  try {
            socket!!.incoming.receiveAsFlow().filter { it is Frame.Text }.map {
                val string = (it as Frame.Text).readText()?: ""
                string
            }?: flow {  }
        }catch (ex:Exception){
            ex.printStackTrace()
            flow {  }
        }
    }

    override suspend fun <T : Any> sendSocketMessage(message: T) {
        try {
            socket?.send(Frame.Text(message.toString()))
        }catch (ex:Exception){
            ex.printStackTrace()
        }
    }

    override suspend fun closeSocketSession() {
        socket?.close()
    }
}