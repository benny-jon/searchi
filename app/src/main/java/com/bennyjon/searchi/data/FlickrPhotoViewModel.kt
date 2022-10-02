package com.bennyjon.searchi.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.*
import com.bennyjon.searchi.network.FlickrApi
import kotlinx.coroutines.flow.map

private const val INITIAL_PAGE = 1
private const val PAGE_SIZE = 100

@ExperimentalPagingApi
class FlickrPhotoViewModel : ViewModel() {

    private var liveData: LiveData<PagingData<FlickrPhotoData>>? = null

    fun getPhotos(
        flickrApi: FlickrApi,
        query: String,
        resetData: Boolean = false
    ): LiveData<PagingData<FlickrPhotoData>> {
        if (liveData == null || resetData) {
            liveData = Pager(config = PagingConfig(
                pageSize = PAGE_SIZE,
                enablePlaceholders = false,
                initialLoadSize = PAGE_SIZE
            ),
                initialKey = INITIAL_PAGE,
                remoteMediator = null,
                pagingSourceFactory = {
                    PhotoPagingSource(flickrApi, query)
                }).flow
                .map { data -> data.map { FlickrPhotoData(it.id, it.getImageUrl()) } }
                .cachedIn(viewModelScope).asLiveData()
        }
        return liveData as LiveData<PagingData<FlickrPhotoData>>
    }
}
