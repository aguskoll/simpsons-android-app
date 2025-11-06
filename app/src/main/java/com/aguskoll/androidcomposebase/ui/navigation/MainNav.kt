package com.aguskoll.androidcomposebase.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.aguskoll.androidcomposebase.ui.pages.error.ErrorPage
import com.aguskoll.androidcomposebase.ui.pages.login.LoginPage
import com.aguskoll.androidcomposebase.ui.pages.main.MainPage
import com.aguskoll.androidcomposebase.ui.theme.AppData

@Composable
fun MainNavHost() {
    val navController = AppData.mainNavController
    NavHost(
        navController = navController,
        startDestination = MainRoutes.LoginPage.route
    ) {
        composable(route = MainRoutes.MainPage.route) { MainPage() }
        composable(route = MainRoutes.LoginPage.route) { backStackEntry ->
            LoginPage(
                onLoginSuccess = {
                    navController.navigate(MainRoutes.MainPage.route) {
                        popUpTo(MainRoutes.LoginPage.route) { inclusive = true }
                    }
                }
            )
        }
        composable(
            route = MainRoutes.ErrorPage.route,
            arguments = listOf(navArgument("message") { type = NavType.StringType })
        ) { backStackEntry ->
            ErrorPage(message = backStackEntry.arguments?.getString("message") ?: "404")
        }
    }
}
