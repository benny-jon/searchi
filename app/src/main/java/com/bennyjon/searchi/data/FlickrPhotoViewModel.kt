package com.bennyjon.searchi.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.*
import com.bennyjon.searchi.models.FlickrPhoto
import com.bennyjon.searchi.network.FlickrApi

private const val INITIAL_PAGE = 1
private const val PAGE_SIZE = 100

@ExperimentalPagingApi
class FlickrPhotoViewModel : ViewModel() {

    private var liveData: LiveData<PagingData<FlickrPhoto>>? = null

    fun getPhotos(flickrApi: FlickrApi, query: String, resetData: Boolean = false): LiveData<PagingData<FlickrPhoto>> {
        if (liveData == null || resetData) {
            liveData = Pager(config = PagingConfig(
                    pageSize = PAGE_SIZE,
                    enablePlaceholders = false,
                    initialLoadSize = PAGE_SIZE),
                    initialKey = INITIAL_PAGE,
                    remoteMediator = null,
                    pagingSourceFactory = {
                        PhotoPagingSource(flickrApi, query)
                    }).flow.cachedIn(viewModelScope).asLiveData()
        }
        return liveData as LiveData<PagingData<FlickrPhoto>>
    }
}
