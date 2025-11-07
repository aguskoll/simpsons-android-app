package com.aguskoll.simpsons.ui.pages.main
//https://thesimpsonsapi.com/#docs
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.LiveTv
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarDefaults
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
 import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.aguskoll.simpsons.ui.navigation.MainRoutes
import com.aguskoll.simpsons.ui.pages.characters.CharactersPage
import com.aguskoll.simpsons.ui.pages.episodes.EpisodesPage

sealed class BottomDest(
    val route: String,
    val label: String,
    val icon: ImageVector
) {
    data object Characters : BottomDest("characters", "Personajes", Icons.Outlined.Person)
    data object Episodes   : BottomDest("episodes",   "Episodios",  Icons.Outlined.LiveTv)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainPage(rootNavController: NavHostController) {
    val tabsNavController  = rememberNavController()
    Scaffold(
        topBar = {
            TopAppBar(title= { Text("The simpsons App") })
        },
        bottomBar = { BottomBar(navController = tabsNavController ) }
    ) { innerPadding->
        NavHost(
            navController = tabsNavController ,
            startDestination = BottomDest.Characters.route,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(route = BottomDest.Characters.route) { CharactersPage(
                onCharacterClick = { character ->
                    rootNavController.navigate(MainRoutes.CharacterDetail.withArg(character.id.toString() )) }
            ) }
            composable(route = BottomDest.Episodes.route) { EpisodesPage() }
        }
    }
}

@Composable
fun BottomBar(navController: NavController){
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
    val items = listOf(BottomDest.Characters, BottomDest.Episodes)

    NavigationBar(windowInsets = NavigationBarDefaults.windowInsets) {
        items.forEach { item ->
            NavigationBarItem(
                selected = currentRoute == item.route,
                onClick = {
                    if (currentRoute != item.route) {
                        navController.navigate(item.route) {
                            launchSingleTop = true
                            popUpTo(navController.graph.startDestinationId) {
                                saveState = true
                            }
                            restoreState = true
                        }
                    }
                },
                icon = { Icon(item.icon, contentDescription = item.label) },
                label = { Text(item.label) }
            )
        }
    }
}
