package com.atfotiad.socketclient.data

import android.os.Build
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.atfotiad.socketclient.model.Message
import com.google.gson.Gson
import io.socket.client.IO
import io.socket.client.Socket
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow


class SocketManager {
    private val scope = CoroutineScope(Dispatchers.IO)
    private var socket: Socket? = null
    private val _mutableListMessages: MutableList<Message> = mutableListOf()
    val listMessages: List<Message> = _mutableListMessages
    val liveDataList: LiveData<List<Message>> = MutableLiveData(listMessages)
    //val flowListMessages = flowOf(listMessages)
    val flowMessage = MutableStateFlow(Message("","",false))
    private val _messages = MutableStateFlow<List<Message>>(emptyList())
    val messages: Flow<List<Message>> = _messages.asStateFlow()

    init {
        socket = if (isEmulator()) {
            IO.socket("http://10.0.2.2:3000")
        } else {
            IO.socket("http://192.168.1.107:3000")
        }
        socket?.connect()
        socket?.on("broadcast") { args ->
            args[0]?.let {
                if (it is String && it.isNotEmpty()) {
                    val message = Gson().fromJson(it.toString(), Message::class.java)
                    _mutableListMessages.add(message)
                    _messages.value += message
                   // flowMessage.value = message
                    Log.d("receiver", "List: $listMessages")
                }
            }
        }
    }

    fun getSocket(): Socket? {
        return socket
    }

    fun disconnect() {
        socket?.disconnect()
    }

    fun isConnected(): Boolean {
        return socket?.connected() ?: false
    }

    fun sendMessage(message: Message) {
        val jsonMessage = Gson().toJson(message, Message::class.java)
        socket?.emit("message", jsonMessage)
    }

    fun onMessageReceived(listener: (Message) -> Unit) {
        socket?.on("broadcast") { args ->
            val message = Gson().fromJson(args[0].toString(), Message::class.java)
            listener(message)
        }
    }

    /*fun registerMessageListener() {
        socket?.on("broadcast") { args ->
            args[0]?.let {
                if (it is String && it.isNotEmpty()) {
                    val message = Gson().fromJson(it.toString(), Message::class.java)
                    _mutableListMessages.add(message)
                    Log.d("receiver", "List: $_mutableListMessages")
                }
            }
        }


    }*/

    private fun isEmulator(): Boolean {
        return Build.MODEL.contains("Emulator")
    }
}