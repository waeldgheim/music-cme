package com.example.musicapp

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.musicapp.ButtonList
import com.example.musicapp.Detail
import com.example.musicapp.screens.screena.ScreenAContent
import com.example.musicapp.screens.screenb.ScreenBContent

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
            ScreenAContent(navController)
        }
        composable("${Detail.route}/{buttonId}") { backStackEntry ->
            val buttonId = backStackEntry.arguments?.getString("buttonId")
            ScreenBContent(buttonId)
        }
    }
}
