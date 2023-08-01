package com.qucoon.rubiesnigeria.repository.socket

import android.annotation.SuppressLint
import com.qucoon.rubiesnigeria.model.chat.Chat
import com.qucoon.rubiesnigeria.model.contacts.Contactslist
import com.qucoon.rubiesnigeria.model.response.fetchfriends.FetchFriendsResponse
import com.qucoon.rubiesnigeria.model.response.fetchfriends.Friend
import com.qucoon.rubiesnigeria.model.response.privatetext.PrivateTextResponse
import com.qucoon.rubiesnigeria.network.EndPoints
import com.qucoon.rubiesnigeria.storage.PaperPrefs
import com.qucoon.rubiesnigeria.storage.getStringPref
import com.qucoon.rubiesnigeria.storage.room.data_source.ChatsDataSource
import com.qucoon.rubiesnigeria.storage.room.data_source.ContactsDataSource
import com.qucoon.rubiesnigeria.storage.room.data_source.FriendsDataSource
import com.qucoon.rubiesnigeria.utils.Constant
import com.qucoon.rubiesnigeria.utils.Utils
import com.qucoon.rubiesnigeria.utils.getObject
import com.tinder.scarlet.Message
import com.tinder.scarlet.WebSocket
import io.reactivex.Flowable
import io.reactivex.disposables.Disposable
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.launch
import org.koin.java.KoinJavaComponent

interface ScarletSocketRepository {
    suspend fun startSocket()
    fun destroySocket()
    fun isSocketAlive(): Boolean
    fun sendMessage(command: String)
    fun observeMessage(): Flow<String>
}

class ScarletSocketRepositoryImpl(val scarletSocket: ScarletSocket,
                                  val contactsDataSource: ContactsDataSource,
                                  val chatsDataSource: ChatsDataSource,
                                  val friendsDataSource: FriendsDataSource

) : ScarletSocketRepository {
    lateinit var disposable: Disposable
    var emptyFlowVariable = flowOf<String>()
    val paperPrefs : PaperPrefs by KoinJavaComponent.inject(PaperPrefs::class.java)



    override suspend fun startSocket() {

        disposable = scarletSocket.observeWebSocketEvent()
            .doOnError { println(it) }
            .subscribe {
                when (it) {
                    is WebSocket.Event.OnConnectionOpened<*> -> {
                        println("OnConnectionOpened")
                    }

                    is WebSocket.Event.OnMessageReceived -> {
                        println("requesting_receieved ${it.message}")
                        println("OnMessageReceived")
                        val message = (it.message as Message.Text).value
                        val response = Utils.getSocketAction(message)
                        when(response){
                            EndPoints.FETCH_FRIEND_ACTION2->{
                                performFriendsAction(message)
                                performFriendsListAction(message)
                            }
                            EndPoints.PRIVATE_TEXT_ACTION ->{
                                performPrivateMessageAction(message)

                            }
                        }
                        emptyFlowVariable = flowOf(message)
                    }

                    is WebSocket.Event.OnConnectionClosing -> {
                        println("OnConnectionClosing")
                    }

                    is WebSocket.Event.OnConnectionClosed -> {
                        println("OnConnectionClosed")
                    }

                    is WebSocket.Event.OnConnectionFailed -> {
                        println("OnConnectionFailed")
                    }
                }
            }
    }

    override fun destroySocket() {
        disposable.dispose()
    }

    override fun isSocketAlive(): Boolean {
        return !disposable.isDisposed
    }

    override fun sendMessage(command: String) {
        scarletSocket.sendCommand(command)
        println("requesting $command")

    }

    override fun observeMessage(): Flow<String> {
        return emptyFlowVariable
    }

    private fun performPrivateMessageAction(message:String){
        val response = message.getObject<PrivateTextResponse>()
        val authenticationProcess = paperPrefs.getStringPref(PaperPrefs.USER_PHONE)+response.sender
        chatsDataSource.updateChat(Chat(0,response.sender!!,paperPrefs.getStringPref(PaperPrefs.USER_PHONE),response.message,authenticationProcess))
    }


    private fun performFriendsAction(message:String) {
        val response = message.getObject<FetchFriendsResponse>()
        println("my_Response $response")
        response.friends?.let {
            it.forEach {
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
        private fun performFriendsListAction(message:String){
            val response = message.getObject<FetchFriendsResponse>()
            println("my_Response $response")
            response.friends?.let {
                GlobalScope.launch{
                    friendsDataSource.updateFriends(response.friends)
                }

            }




    }

}