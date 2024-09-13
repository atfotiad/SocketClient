package com.atfotiad.socketclient.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.atfotiad.socketclient.ChatViewModel


@Composable
fun JoinScreen(
    viewModel: ChatViewModel,
    modifier: Modifier = Modifier,
    onJoinClicked: (String) -> Unit
) {
    var username by remember { mutableStateOf("") }

    Box(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        TextField(
            value = username,
            onValueChange = { username = it },
            label = { Text("Enter username") },
            modifier = modifier
                .fillMaxWidth()
                .align(Alignment.Center),
            singleLine = true,
            trailingIcon = {
                IconButton(onClick = {
                    viewModel.saveUsername(username)
                    onJoinClicked(username)
                }) {
                    Icon(Icons.AutoMirrored.Filled.Send, contentDescription = "Send")
                }
            }
        )
    }

}