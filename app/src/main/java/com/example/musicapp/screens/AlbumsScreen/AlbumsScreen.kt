package com.example.musicapp.screens.AlbumsScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Palette
import androidx.compose.material.icons.filled.Refresh
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.musicapp.Detail
import com.example.musicapp.R
import com.example.musicapp.database.DatabaseAlbum
import com.example.musicapp.repository.ApiStatus
import com.example.musicapp.screens.components.ColorPickerDialog
import com.example.musicapp.screens.components.ErrorScreen
import com.example.musicapp.screens.components.LoadingAnimation
import com.example.musicapp.ui.theme.MusicAppTheme
import kotlinx.coroutines.flow.StateFlow

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AlbumScreen(navController: NavController) {
    val viewModel: AlbumsViewModel = hiltViewModel()
    val albumList by viewModel.albums.collectAsState(initial = emptyList())

    val color by viewModel.color.collectAsState()

    MusicAppTheme(color = color) {
        AlbumsGrid(viewModel, albumList, navController, viewModel.status)
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AlbumsGrid(
    viewModel: AlbumsViewModel,
    albumList: List<DatabaseAlbum>,
    navController: NavController,
    statusApi: StateFlow<ApiStatus>
) {
    var showDialog by remember { mutableStateOf(false) }
    val status by statusApi.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Top 100 Albums",
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                },
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
            when (status) {
                ApiStatus.LOADING ->
                    LoadingAnimation()

                ApiStatus.ERROR ->
                    ErrorScreen(onRetry = { viewModel.refresh() })

                else ->
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
        }
    )

    ColorPickerDialog(showDialog, { showDialog = false }, viewModel)
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
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(album.imageUrl)
                        .error(R.drawable.baseline_cloud_off_24)
                        .placeholder(R.drawable.baseline_loop_24)
                        .build(),
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxSize()
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
                            color = Color.Black.copy(alpha = 0.7f)
                        ),
                    verticalArrangement = Arrangement.Bottom
                ) {
                    Text(
                        text = album.name,
                        color = Color.White,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        style = TextStyle(lineHeight = 0.sp),
                        modifier = Modifier.padding(start = 10.dp, top = 5.dp)
                    )
                    Text(
                        text = album.artistName,
                        color = Color.White.copy(0.7f),
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        style = TextStyle(lineHeight = 0.sp),
                        modifier = Modifier.padding(start = 10.dp, bottom = 10.dp),
                    )
                }
            }
        }
    }
}
