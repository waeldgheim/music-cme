package com.example.musicapp.screens.components


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.musicapp.Home
import com.example.musicapp.Search
import com.example.musicapp.Settings
import com.example.musicapp.ui.theme.MusicAppTheme
import com.example.musicapp.ui.theme.Theme

@Composable
fun MusicBottomAppBar(navController: NavHostController) {
    val selectedRoute = remember { mutableStateOf(Home.route) }
    MusicAppTheme(color = Theme.getTColor()) {
        BottomAppBar(
            containerColor = MaterialTheme.colorScheme.tertiary,
            contentColor = MaterialTheme.colorScheme.onTertiary,
            modifier = Modifier.height(60.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(bottom = 3.dp)
                    .padding(horizontal = 24.dp),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                val selectedColor = MaterialTheme.colorScheme.onTertiary
                val unselectedColor = selectedColor.copy(alpha = 0.6f) // Adjust alpha for transparency

                IconButton(
                    onClick = {
                        selectedRoute.value = Home.route
                        navController.navigate(Home.route) {
                            popUpTo(Home.route) { inclusive = true }
                        }
                    }, modifier = Modifier.weight(1f)
                ) {
                    val isSelected = selectedRoute.value == Home.route
                    Icon(
                        Icons.Filled.Home,
                        contentDescription = "Home",
                        tint = if (isSelected) selectedColor else unselectedColor,
                        modifier = Modifier.fillMaxSize(0.8f)
                    )
                }

                IconButton(
                    onClick = {
                        selectedRoute.value = Search.route
                        navController.navigate(Search.route) {
                            popUpTo(Search.route) { inclusive = true }
                        }
                    }, modifier = Modifier.weight(1f)
                ) {
                    val isSelected = selectedRoute.value == Search.route
                    Icon(
                        Icons.Filled.Search,
                        contentDescription = "Search",
                        tint = if (isSelected) selectedColor else unselectedColor,
                        modifier = Modifier.fillMaxSize(0.8f)
                    )
                }

                IconButton(
                    onClick = {
                        selectedRoute.value = Settings.route
                        navController.navigate(Settings.route) {
                            popUpTo(Settings.route) { inclusive = true }
                        }
                    }, modifier = Modifier.weight(1f)
                ) {
                    val isSelected = selectedRoute.value == Settings.route
                    Icon(
                        Icons.Filled.Settings,
                        contentDescription = "Settings",
                        tint = if (isSelected) selectedColor else unselectedColor,
                        modifier = Modifier.fillMaxSize(0.8f)
                    )
                }
            }
        }
    }
}
