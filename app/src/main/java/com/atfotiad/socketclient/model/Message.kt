package com.atfotiad.socketclient.model

data class Message(
    val username: String,
    val text: String,
    var isSelf: Boolean = false
)
