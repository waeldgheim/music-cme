package com.example.musicapp


import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.example.musicapp.screens.components.MusicBottomAppBar
import com.example.musicapp.ui.theme.MusicAppTheme
import com.example.musicapp.ui.theme.Theme
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Theme.initialize(this)
        setContent {
            MusicAppTheme(color = Theme.getTColor()) {
                val navController = rememberNavController()
                Scaffold(
                    bottomBar = {
                        MusicBottomAppBar(navController = navController)
                    }
                ) { innerPadding ->
                    MusicNavHost(
                        navController = navController,
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}
