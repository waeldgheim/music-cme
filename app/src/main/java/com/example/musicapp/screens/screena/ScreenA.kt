package com.example.musicapp.screens.screena

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Palette
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.musicapp.Detail
import com.example.musicapp.database.DatabaseAlbum
import com.example.musicapp.repository.ApiStatus
import com.example.musicapp.screens.components.GlideImage
import com.example.musicapp.ui.theme.LoadingAnimation
import com.example.musicapp.ui.theme.MusicAppTheme
import com.example.musicapp.ui.theme.Theme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScreenAContent(navController: NavController) {
    val viewModel: ScreenAViewModel = hiltViewModel()
    val albumList by viewModel.albums.collectAsState(initial = emptyList())
    val status by viewModel.status.collectAsState()

    val color by viewModel.color.collectAsState()

    MusicAppTheme(color = color) {
        when (status) {
            ApiStatus.LOADING -> {
                LoadingAnimation()
            }

            ApiStatus.ERROR -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Filled.Refresh,
                        contentDescription = "Error icon",
                        modifier = Modifier
                            .size(200.dp)
                            .padding(16.dp)
                            .clickable { viewModel.refresh() },
                        tint = MaterialTheme.colorScheme.primary
                    )
                }
            }

            ApiStatus.DONE -> {
                ScreenA(viewModel, albumList, navController)
            }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScreenA(
    viewModel: ScreenAViewModel,
    albumList: List<DatabaseAlbum>,
    navController: NavController,
) {
    var showDialog by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Top 100 Albums") },
                actions = {
                    IconButton(onClick = { showDialog = true }) {
                        Icon(
                            imageVector = Icons.Filled.Palette,
                            contentDescription = "Choose Color",
                            tint = MaterialTheme.colorScheme.onPrimary
                        )
                    }
                    IconButton(onClick = { viewModel.refresh() }) {
                        Icon(
                            imageVector = Icons.Filled.Refresh,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.onPrimary
                        )
                    }
                },
                colors = TopAppBarDefaults.largeTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary,
                    actionIconContentColor = MaterialTheme.colorScheme.onPrimary
                )
            )
        },
        content = { innerPadding ->
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                contentPadding = PaddingValues(
                    start = 15.dp,
                    end = 15.dp,
                    top = 25.dp,
                    bottom = 8.dp
                ),
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
            ) {
                items(albumList) { album ->
                    AlbumItem(album = album, navController = navController)
                }
            }
        }
    )
    val colorOptions = listOf(
        0xFF00FFFF, 0xFF15f9a6, 0xFFFF1493, 0xFFFF7F50, 0xFF40E0D0, 0xFFFF7F50,
        0xFF6A5ACD, 0xFFBA55D3, 0xFFFF4500, 0xFF8A2BE2, 0xFFDE3163, 0xFF5F9EA0,
        0xFF7FFF00, 0xFFD2691E, 0xFFFF7F24, 0xFFDC143C, 0xFF00008B, 0xFF008B8B,
        0xFFB8860B, 0xFFA9A9A9, 0xFF006400, 0xFF8B008B, 0xFF8B0000, 0xFF483D8B, 0xFF6495ED
    )

    val colorsPerRow = 5

    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            title = { Text(text = "Choose Color") },
            text = {
                Column {
                    colorOptions.chunked(colorsPerRow).forEach { rowColors ->
                        Row {
                            rowColors.forEach { colorHex ->
                                ColorOption(Color(colorHex)) { c ->
                                    Theme.saveColor(c.toArgb().toLong())
                                    viewModel.updateColor(c.toArgb().toLong())
                                    showDialog = false
                                }
                            }
                        }
                    }
                }
            },
            confirmButton = {}
        )
    }
}

@Composable
fun ColorOption(color: Color, onColorSelected: (Color) -> Unit) {
    Box(
        modifier = Modifier
            .size(50.dp)
            .padding(4.dp)
            .background(color)
            .clickable { onColorSelected(color) }
    )
}


@Composable
fun AlbumItem(album: DatabaseAlbum, navController: NavController) {
    val roundedCornerShape = RoundedCornerShape(16.dp)
    Surface(
        shape = roundedCornerShape,
        color = MaterialTheme.colorScheme.surface,
        modifier = Modifier
            .padding(8.dp)
            .clip(roundedCornerShape)
            .clickable { navController.navigate("${Detail.route}/${album.id}") }
    ) {
        Box(
            modifier = Modifier
                .aspectRatio(1f)
                .clip(roundedCornerShape)
        ) {
            album.imageUrl?.let {
                GlideImage(
                    imageUrl = it,
                    contentDescription = null,
                    modifier = Modifier.fillMaxSize()
                )
            }

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(0.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.BottomStart)
                        .background(
                            color = Color.Black.copy(alpha = 0.6f)
                        ),
                    verticalArrangement = Arrangement.Bottom
                ) {
                    Text(
                        text = album.name,
                        color = MaterialTheme.colorScheme.onPrimary,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier.padding(start = 8.dp)
                    )
                    Text(
                        text = album.artistName,
                        color = MaterialTheme.colorScheme.onPrimary,
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier.padding(start = 8.dp)
                    )
                }
            }
        }
    }
}