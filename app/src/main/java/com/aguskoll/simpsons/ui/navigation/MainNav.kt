package com.aguskoll.simpsons.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.aguskoll.simpsons.ui.pages.characters.detail.CharacterDetailPage
import com.aguskoll.simpsons.ui.pages.error.ErrorPage
import com.aguskoll.simpsons.ui.pages.login.LoginPage
import com.aguskoll.simpsons.ui.pages.main.MainPage
import com.aguskoll.simpsons.ui.theme.AppData

@Composable
fun MainNavHost() {
    val navController = AppData.mainNavController
    NavHost(
        navController = navController,
        startDestination = MainRoutes.MainPage.route
    ) {
        composable(route = MainRoutes.MainPage.route) { MainPage(navController) }
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
        composable(MainRoutes.CharacterDetail.route, arguments = listOf(navArgument("id") { type = NavType.StringType })) { backStackEntry ->
            val id = backStackEntry.arguments?.getString("id") ?: ""
            CharacterDetailPage(characterId = id)
        }
    }
}
