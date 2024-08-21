package com.example.musicapp

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.musicapp.screens.AlbumDetailsScreen.AlbumDetailsScreen
import com.example.musicapp.screens.AlbumsScreen.AlbumsScreen

@Composable
fun MusicNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        modifier = modifier,
        startDestination = ButtonList.route
    ) {
        composable(ButtonList.route) {
            AlbumsScreen(navController)
        }
        composable("${Detail.route}/{albumId}") { backStackEntry ->
            val albumId = backStackEntry.arguments?.getString("albumId")
            AlbumDetailsScreen(albumId, navController)
        }
    }
}
