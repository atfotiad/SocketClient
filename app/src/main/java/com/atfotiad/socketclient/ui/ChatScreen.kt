package com.atfotiad.socketclient.ui

import android.util.Log
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.ime
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.atfotiad.socketclient.ChatViewModel
import com.atfotiad.socketclient.UiState
import com.atfotiad.socketclient.model.Message

@Composable
fun ChatScreen(viewModel: ChatViewModel) {

    val uiState by viewModel.messages.collectAsStateWithLifecycle()
    Log.d("uiState", "ChatScreen: $uiState")

    ConstraintLayout(
        Modifier
            .fillMaxSize()
    ) {
        val (chatList, chatInput) = createRefs()
        val imeBottomDp = with(LocalDensity.current) { WindowInsets.ime.getBottom(this).toDp() }

        ChatList(list = (uiState as UiState.Success).messages,
            Modifier
                .constrainAs(chatList) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    bottom.linkTo(chatInput.top)
                    width = Dimension.fillToConstraints
                    height = Dimension.preferredWrapContent
                }

        )
        ChatBox(Modifier.constrainAs(chatInput) {
            bottom.linkTo(parent.bottom, imeBottomDp)
            start.linkTo(parent.start)
            end.linkTo(parent.end)

        }) {
            val username = viewModel.getUsername()
            viewModel.sendMessage(Message(username.toString(), it))
        }
    }
}