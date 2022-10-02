package com.bennyjon.searchi.ui.compose

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.LoadStates
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import coil.compose.AsyncImage
import com.bennyjon.searchi.R
import com.bennyjon.searchi.data.FlickrPhotoData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow

private const val GRID_COLUMNS = 3
private const val EMPTY = ""

@Composable
fun SearchFlickrImagesScreen(
    imagesList: Flow<PagingData<FlickrPhotoData>> = emptyFlow(),
    searchInputState: SearchBarInputState,
    onSearchQueryChanged: (String) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        SearchBar(searchInputState = searchInputState, onSearchQueryChanged = onSearchQueryChanged)
        FlickrImagesPagingGrid(imagesList)
    }
}

@Composable
fun FlickrImage(imageUrl: String) {
    AsyncImage(
        model = imageUrl,
        contentDescription = null,
        error = painterResource(id = R.drawable.ic_image),
        placeholder = painterResource(id = R.drawable.ic_image),
        modifier = Modifier
            .size(dimensionResource(id = R.dimen.imageSize))
            .clip(RoundedCornerShape(2.dp))
            .padding(4.dp),
        contentScale = ContentScale.Crop
    )
}

@Composable
fun FlickrImagesPagingGrid(imagesList: Flow<PagingData<FlickrPhotoData>> = emptyFlow()) {
    val imagesItems: LazyPagingItems<FlickrPhotoData> = imagesList.collectAsLazyPagingItems()
    imagesItems.loadState.source.refresh

    if (imagesItems.itemCount > 0) {
        LazyVerticalGrid(
            columns = GridCells.Fixed(GRID_COLUMNS)
        ) {
            items(imagesItems.itemCount) { index ->
                imagesItems[index]?.let { data ->
                    FlickrImage(imageUrl = data.imageUrl)
                }
            }
        }
    } else {
        Box(
            modifier = Modifier.fillMaxHeight(),
            contentAlignment = Alignment.Center
        ) {
            when (imagesItems.loadState.refresh) {
                LoadState.Loading -> {
                    // Show between Searches
                    CircularProgressIndicator()
                }
                LoadState.NotLoading(endOfPaginationReached = true) -> {
                    // Shows only when no searches has been made
                    Text(
                        modifier = Modifier
                            .padding(8.dp)
                            .padding(bottom = 24.dp),
                        textAlign = TextAlign.Center,
                        style = MaterialTheme.typography.subtitle1,
                        text = stringResource(id = R.string.empty_screen_message)
                    )
                }
                else -> {}
            }
        }
    }
}

@Preview(showSystemUi = true)
@Composable
fun ScreenPreview() {
    MaterialTheme {
        SearchFlickrImagesScreen(
            searchInputState = rememberSearchBarInputState(
                placeholder = stringResource(id = R.string.placeholder_search),
                initialText = EMPTY
            ),
            onSearchQueryChanged = {}
        )
    }
}

@Preview(showSystemUi = true)
@Composable
fun FlickrImagesPreview() {
    MaterialTheme {
        LazyVerticalGrid(
            columns = GridCells.Fixed(GRID_COLUMNS),
            contentPadding = PaddingValues(8.dp)
        ) {
            for (x in 1..20) {
                item { FlickrImage(imageUrl = EMPTY) }
            }
        }
    }
}

fun notLoadingStates(): LoadStates {
    return LoadStates(
        refresh = LoadState.NotLoading(true),
        append = LoadState.NotLoading(true),
        prepend = LoadState.NotLoading(true)
    )
}
