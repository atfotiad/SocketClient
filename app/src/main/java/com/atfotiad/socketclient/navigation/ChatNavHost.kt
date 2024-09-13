package com.atfotiad.socketclient.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.atfotiad.socketclient.ChatViewModel
import com.atfotiad.socketclient.MainActivity
import com.atfotiad.socketclient.ui.ChatScreen
import com.atfotiad.socketclient.ui.JoinScreen

@Composable
fun ChatNavHost(
    navController: NavHostController,
    viewModel: ChatViewModel
) {
    NavHost(
        navController = navController,
        startDestination = JoinScreen.route,
        modifier = Modifier
    ) {
        composable(route = JoinScreen.route) {
            JoinScreen(viewModel) {
                navController.navigate("chat/$it")
            }
        }
        composable(
            route = ChatScreen.routeWithArgs,
            arguments = ChatScreen.arguments
        ) { navBackStackEntry ->
            val username = navBackStackEntry.arguments?.getString(ChatScreen.USERNAME_ARG)
            if (username != null &&
                username == viewModel.getUsername()
            ) {
                ChatScreen(viewModel)
            } else {
                (LocalContext.current as MainActivity).finish()
            }
        }


    }
}