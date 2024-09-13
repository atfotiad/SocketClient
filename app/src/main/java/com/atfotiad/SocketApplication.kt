package com.atfotiad

import android.app.Application
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.rememberNavController
import com.atfotiad.socketclient.navigation.ChatNavHost
import com.atfotiad.socketclient.ChatViewModel
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class SocketApplication : Application() {

    companion object {
        @Composable
        fun ChatApp() {

            Surface(
                Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.background)
                    .systemBarsPadding()
            ) {
                val navController = rememberNavController()
                val viewModel: ChatViewModel = hiltViewModel()

                ChatNavHost(
                    navController = navController, viewModel
                )
            }

        }
    }

}
