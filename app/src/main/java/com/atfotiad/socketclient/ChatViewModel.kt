package com.atfotiad.socketclient

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.atfotiad.socketclient.data.PreferencesRepository
import com.atfotiad.socketclient.data.SocketManager
import com.atfotiad.socketclient.model.Message
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChatViewModel @Inject constructor(
    private val repository: PreferencesRepository,
    private val socketManager: SocketManager
) : ViewModel(
) {
    private val _messages = MutableStateFlow<UiState>(UiState.Success(emptyList()))
    val messages: StateFlow<UiState> = _messages.asStateFlow()


    init {
        viewModelScope.launch {
            socketManager.messages.collect {
                Log.d("ChatViewModel", "Collected From SocketManager: $it")
                it.forEach { message ->
                    if (message.username == getUsername()) {
                        message.isSelf = true
                    }
                }
                _messages.value = UiState.Success(it)
            }
        }
    }

    fun saveUsername(username: String) {
        viewModelScope.launch {
            repository.saveUsername(username)
        }
    }

    fun getUsername(): String? {
        return repository.getUsername()
    }

    fun sendMessage(message: Message) {
        socketManager.sendMessage(message)
    }


}

sealed interface UiState {
    data class Success(val messages: List<Message>) : UiState
}