package com.bennyjon.searchi.ui.compose

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.asFlow
import androidx.paging.ExperimentalPagingApi
import androidx.paging.PagingData
import com.bennyjon.searchi.R
import com.bennyjon.searchi.data.FlickrPhotoViewModel
import com.bennyjon.searchi.network.FlickrApi
import dagger.android.AndroidInjection
import javax.inject.Inject
import kotlinx.coroutines.flow.flowOf

private const val EMPTY = ""

@OptIn(ExperimentalPagingApi::class)
class ComposeActivity : AppCompatActivity() {

    @Inject
    internal lateinit var flickrApi: FlickrApi

    private val viewModel: FlickrPhotoViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)

        setContent {
            MaterialTheme {
                val searchInputState = rememberSearchBarInputState(
                    placeholder = stringResource(id = R.string.placeholder_search),
                    initialText = EMPTY
                )

                val pagingData = remember {
                    mutableStateOf(
                        if (searchInputState.text == EMPTY) {
                            flowOf(
                                PagingData.empty(
                                    sourceLoadStates = notLoadingStates(),
                                    mediatorLoadStates = notLoadingStates()
                                )
                            )
                        } else {
                            // Restore the existing PagingData after screen rotations
                            viewModel.getPhotos(flickrApi, query = searchInputState.text).asFlow()
                        }
                    )
                }

                SearchFlickrImagesScreen(
                    imagesList = pagingData.value,
                    searchInputState = searchInputState,
                    onSearchQueryChanged = {
                        pagingData.value =
                            viewModel.getPhotos(flickrApi, query = it, resetData = true).asFlow()
                    })
            }
        }
    }
}
