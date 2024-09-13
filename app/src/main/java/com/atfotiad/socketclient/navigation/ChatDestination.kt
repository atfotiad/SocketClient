package com.atfotiad.socketclient.navigation

import androidx.navigation.NavType
import androidx.navigation.navArgument

interface ChatDestination {
    val route: String
}

object JoinScreen: ChatDestination {
    override val route: String = "join"
}

object ChatScreen: ChatDestination {
    override val route: String = "chat"
    const val USERNAME_ARG = "username"
    val routeWithArgs = "$route/{$USERNAME_ARG}"
    val arguments = listOf(navArgument(USERNAME_ARG) { type = NavType.StringType })
}