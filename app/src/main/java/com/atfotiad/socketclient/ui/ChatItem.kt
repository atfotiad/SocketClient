package com.atfotiad.socketclient.ui


import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.atfotiad.socketclient.model.Message

@Composable
fun ChatItem(item: Message, modifier: Modifier = Modifier) {
    Box(
        modifier = Modifier.fillMaxWidth().padding(8.dp),
        contentAlignment = if (item.isSelf) Alignment.TopEnd else Alignment.TopStart
    ) {
        Card(
            elevation = CardDefaults.cardElevation(4.dp),
            colors = CardDefaults.cardColors(),
            shape = if (item.isSelf)
                RoundedCornerShape(
                    topStart = 15.dp,
                    topEnd = 4.dp,
                    bottomStart = 15.dp,
                    bottomEnd = 15.dp
                ) else RoundedCornerShape(
                    topStart = 4.dp,
                    topEnd = 15.dp,
                    bottomStart = 15.dp,
                    bottomEnd = 15.dp
                )

        ) {
            Column(
                modifier.padding(8.dp)
            ) {
                Text(text = item.username)
                Text(text = item.text)
            }
        }
    }

}