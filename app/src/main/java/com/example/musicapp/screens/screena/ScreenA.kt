package com.example.musicapp.screens.screena

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController

@Composable
fun ScreenAContent(navController: NavController) {

    val viewModel = hiltViewModel<ScreenAViewModel>()
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Text(
            text = viewModel.test,
            style = TextStyle(fontSize = 27.sp),
            modifier = Modifier
                .align(Alignment.TopCenter)
        )
    }
    LazyColumn {
        items(10) { index ->
            Button(
                onClick = {
                    navController.navigate("detail/$index")
                },
                modifier = Modifier
                    .padding(8.dp)
            ) {
                Text(text = "Button $index")
            }
        }
    }
}
