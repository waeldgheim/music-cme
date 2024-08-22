package com.example.musicapp

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.musicapp.screens.AlbumDetailsScreen.AlbumDetailsScreen
import com.example.musicapp.screens.AlbumsScreen.AlbumScreen
import com.example.musicapp.screens.FilterScreen.FilterScreen
import com.example.musicapp.screens.SettingsScreen.SettingsScreen

@Composable
fun MusicNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        modifier = modifier,
        startDestination = Home.route
    ) {
        composable(Home.route) {
            AlbumScreen(navigateToAlbumDetails = {albumId -> navController.navigate("${Detail.route}/${albumId}")})
        }
        composable(Filter.route) {
            FilterScreen(navigateToAlbumDetails = {albumId -> navController.navigate("${Detail.route}/${albumId}")})
        }
        composable(Settings.route) {
            SettingsScreen()
        }
        composable("${Detail.route}/{albumId}") { backStackEntry ->
            val albumId = backStackEntry.arguments?.getString("albumId")
            AlbumDetailsScreen(albumId, navigateUp = { navController.navigateUp() })
        }
    }
}
