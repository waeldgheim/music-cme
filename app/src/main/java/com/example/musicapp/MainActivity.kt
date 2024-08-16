package com.example.musicapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.rememberNavController
import com.example.musicapp.ui.theme.MusicAppTheme
import com.example.musicapp.ui.theme.Theme
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Theme.initialize(this)
        setContent {
            MusicAppTheme(color = Theme.getTColor()){
                val navController = rememberNavController()
                MusicNavHost(navController = navController)
            }
        }
    }
}
