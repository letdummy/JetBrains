package com.sekalisubmit.jetbrains

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.sekalisubmit.jetbrains.ui.navigation.NavigationItem
import com.sekalisubmit.jetbrains.ui.navigation.Screen
import com.sekalisubmit.jetbrains.ui.screen.detail.DetailScreen
import com.sekalisubmit.jetbrains.ui.screen.favorite.FavoriteScreen
import com.sekalisubmit.jetbrains.ui.screen.home.HomeScreen
import com.sekalisubmit.jetbrains.ui.screen.profile.ProfileScreen
import com.sekalisubmit.jetbrains.ui.screen.setting.SettingScreen

@Composable
fun JetBrainsApp(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    Scaffold(
        bottomBar = {
            if (currentRoute != Screen.Detail.route) {
                BottomBar(
                    navController = navController,
                    modifier = modifier
                        .padding(10.dp)
                        .clip(RoundedCornerShape(20.dp)),
                )
            }
        },
        modifier = modifier
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Screen.Home.route,
            modifier = modifier.padding(innerPadding)
        ) {
            composable(Screen.Home.route) {
                HomeScreen(
                    navController = navController,
                    navigateToDetail = { ideId ->
                        navController.navigate(Screen.Detail.createRoute(ideId))
                    }
                )
            }
            composable(Screen.Favorite.route) {
                FavoriteScreen(
                    navigateToDetail = { ideId ->
                        navController.navigate(Screen.Detail.createRoute(ideId))
                    }
                )
            }
            composable(Screen.Profile.route) {
                ProfileScreen()
            }
            composable(Screen.Setting.route) {
                SettingScreen()
            }
            composable(
                Screen.Detail.route,
                arguments = listOf(navArgument("ideId") { type = NavType.LongType }),) {
                DetailScreen(
                    ideId = it.arguments?.getLong("ideId") ?: 1L,
                    navigateBack = { navController.navigateUp() },
                )
            }
        }
    }
}

@Composable
private fun BottomBar(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavigationBar(
        modifier = modifier,
        containerColor = Color(0xFF121314),
    ) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route
        val navigationItems = listOf(
            NavigationItem(
                title = stringResource(R.string.menu_home),
                icon = Icons.Default.Home,
                screen = Screen.Home
            ),
            NavigationItem(
                title = stringResource(R.string.menu_favorites),
                icon = Icons.Default.Favorite,
                screen = Screen.Favorite
            ),
            NavigationItem(
                title = stringResource(R.string.menu_profile),
                icon = Icons.Default.Person,
                screen = Screen.Profile
            ),
            NavigationItem(
                title = stringResource(R.string.menu_settings),
                icon = Icons.Default.Settings,
                screen = Screen.Setting
            )
        )
        navigationItems.map { item ->
            NavigationBarItem(
                modifier = Modifier,
                icon = {
                    Icon(
                        imageVector = item.icon,
                        contentDescription = item.title,
                        modifier = Modifier.testTag(item.screen.route)
                    )
                },
                label = { Text(item.title) },
                selected = currentRoute == item.screen.route,
                onClick = {
                    navController.navigate(item.screen.route) {
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        restoreState = true
                        launchSingleTop = true
                    }
                },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = Color(0xFF121314),
                    selectedTextColor = Color(0xFF989899),
                    indicatorColor = Color(0xFF989899),
                )
            )
        }
    }
}