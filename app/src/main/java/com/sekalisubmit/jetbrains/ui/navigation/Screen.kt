package com.sekalisubmit.jetbrains.ui.navigation

sealed class Screen(val route: String) {
    data object Detail : Screen("home/{ideId}") {
        fun createRoute(ideId: Long) = "home/$ideId"
    }
    data object Favorite : Screen("favorite")
    data object Home : Screen("home")
    data object Profile : Screen("profile")
    data object Setting : Screen("setting")
}
