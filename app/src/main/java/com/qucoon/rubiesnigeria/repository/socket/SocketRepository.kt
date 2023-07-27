package com.qucoon.rubiesnigeria.repository.socket

import com.qucoon.rubiesnigeria.network.BaseSocketRepository
import com.qucoon.rubiesnigeria.network.NetworkProvider
import com.qucoon.rubiesnigeria.network.NetworkResult
import kotlinx.coroutines.flow.Flow

interface SocketRepository {
    suspend fun openSocket(): NetworkResult<Unit>
    suspend fun sendMessage(message:String)
    suspend  fun observeRequest(): Flow<String>
    suspend  fun observeRequestString(): String
    suspend fun closeSession()
}

class SocketRepositoryImpl(networkProvider: NetworkProvider) :SocketRepository, BaseSocketRepository(networkProvider) {

    override suspend fun openSocket():NetworkResult<Unit> {
        return openSocketSession()
    }

    override suspend fun sendMessage(message: String) {
        sendSocketMessage<String>(message)
    }

    override suspend fun observeRequest(): Flow<String> {
        return  observeSocketMessage()
    }

    override suspend fun observeRequestString(): String {
        return observeRequestString()
    }

    override suspend fun closeSession(){
        closeSocketSession()
    }

}