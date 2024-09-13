package com.atfotiad.socketclient.ui

import android.util.Log
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.unit.dp
import com.atfotiad.socketclient.model.Message
import kotlinx.coroutines.launch

@Composable
fun ChatList(list: List<Message>, modifier: Modifier = Modifier) {
    val lazyListState = rememberLazyListState()
    val previousHeight by remember { mutableIntStateOf(0) }
    val coroutineScope = rememberCoroutineScope()

    Log.d("ChatList", "ChatList:  $list")
    LazyColumn(
        modifier = modifier
            .fillMaxHeight()
            .padding(16.dp)
            .onSizeChanged { size ->
                if (size.height > previousHeight) {
                    coroutineScope.launch {
                        if (list.isNotEmpty()) {
                            lazyListState.animateScrollToItem(list.lastIndex)
                        }
                    }
                }
            },
        state = lazyListState
    ) {
        items(count = list.size) { index ->
            val message = list[index]
            ChatItem(item = message)
        }
    }
}
