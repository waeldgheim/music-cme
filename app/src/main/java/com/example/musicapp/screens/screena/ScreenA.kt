package com.example.musicapp.screens.screena

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun ScreenAContent() {
    LazyColumn {
        items(10) { index ->
            Button(
                onClick = {

                },
                modifier = Modifier
                    .padding(8.dp)
            ) {
                Text(text = "Button $index")
            }
        }
    }
}
