package com.example.musicapp.screens.FilterScreen

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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Palette
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.musicapp.R
import com.example.musicapp.database.DatabaseAlbum
import com.example.musicapp.repository.ApiStatus
import com.example.musicapp.screens.components.ColorPickerDialog
import com.example.musicapp.screens.components.ErrorScreen
import com.example.musicapp.screens.components.LoadingAnimation
import com.example.musicapp.ui.theme.MusicAppTheme
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FilterScreen(navigateToAlbumDetails: (String) -> Unit) {
    val viewModel: FilterViewModel = hiltViewModel()
    val color by viewModel.color.collectAsState()
    val allGenres by viewModel.allGenres.collectAsState()
    val selectedGenre by viewModel.selectedGenre.collectAsState()
    val filteredAlbums by viewModel.filteredAlbums.collectAsState()
    val status by viewModel.status.collectAsState()

    var showDialog by remember { mutableStateOf(false) }

    val isRefreshing by viewModel.isRefreshing.collectAsState()
    val swipeRefreshState = rememberSwipeRefreshState(isRefreshing)

    MusicAppTheme(color = color) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = {
                        Text(
                            text = "Filter by Genre",
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
                    },
                    colors = TopAppBarDefaults.largeTopAppBarColors(
                        containerColor = MaterialTheme.colorScheme.primary,
                        titleContentColor = MaterialTheme.colorScheme.onPrimary,
                        actionIconContentColor = MaterialTheme.colorScheme.onPrimary
                    )
                )
            },
            content = { innerPadding ->
                SwipeRefresh(
                    state = swipeRefreshState,
                    onRefresh = {
                        viewModel.refresh()
                    }
                ) {
                    when (status) {
                        ApiStatus.LOADING -> LoadingAnimation()
                        ApiStatus.ERROR -> ErrorScreen(onRetry = { viewModel.refresh() })
                        else ->
                            Column(
                                modifier = Modifier
                                    .padding(innerPadding)
                                    .padding(top = 15.dp)
                            )
                            {
                                LazyRow(
                                    horizontalArrangement = Arrangement.spacedBy(5.dp),
                                    contentPadding = PaddingValues(horizontal = 12.dp)
                                )
                                {
                                    item {
                                        FilterButton(
                                            "All",
                                            selectedGenre,
                                            onGenreSelected = { g -> viewModel.selectGenre(g) })
                                    }
                                    items(allGenres) { genre ->
                                        FilterButton(genre, selectedGenre,
                                            onGenreSelected = { g -> viewModel.selectGenre(g) })
                                    }
                                }

                                LazyColumn(
                                    contentPadding = PaddingValues(
                                        start = 20.dp,
                                        end = 20.dp,
                                        top = 10.dp,
                                        bottom = 8.dp
                                    ),
                                    modifier = Modifier
                                        .fillMaxSize()
                                ) {
                                    items(filteredAlbums) { album ->
                                        AlbumItem(
                                            album = album,
                                            navigateToAlbumDetails = navigateToAlbumDetails
                                        )
                                    }
                                }
                            }
                    }
                }
            }
        )
    }
    ColorPickerDialog(showDialog, { showDialog = false }, { c -> viewModel.updateColor(c) })
}

@Composable
fun FilterButton(genre: String, selectedGenre: String, onGenreSelected: (String) -> Unit) {
    Button(
        onClick = { onGenreSelected(genre) },
        colors = if (genre == selectedGenre) ButtonDefaults.buttonColors(MaterialTheme.colorScheme.primary) else ButtonDefaults.buttonColors(
            MaterialTheme.colorScheme.secondary
        )
    ) {
        Text(
            text = genre,
            color = MaterialTheme.colorScheme.onPrimary
        )
    }
}

@Composable
fun AlbumItem(album: DatabaseAlbum, navigateToAlbumDetails: (String) -> Unit) {
    val roundedCornerShape = RoundedCornerShape(16.dp)
    Surface(
        shape = roundedCornerShape,
        color = MaterialTheme.colorScheme.surface,
        modifier = Modifier
            .padding(8.dp)
            .clip(roundedCornerShape)
            .clickable { navigateToAlbumDetails(album.id) }
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
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier
                            .padding(start = 10.dp)
                            .padding(top = 15.dp)
                            .padding(bottom = 5.dp)
                    )
                    Text(
                        text = album.artistName,
                        color = Color.White.copy(0.7f),
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier
                            .padding(start = 10.dp)
                            .padding(bottom = 15.dp)
                    )
                }
            }
        }
    }
}
