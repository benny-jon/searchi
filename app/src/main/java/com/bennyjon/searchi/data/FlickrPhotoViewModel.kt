package com.bennyjon.searchi.data

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.ViewModel
import android.arch.paging.LivePagedListBuilder
import android.arch.paging.PagedList
import com.bennyjon.searchi.models.FlickrPhoto
import com.bennyjon.searchi.network.FlickrApi

class FlickrPhotoViewModel : ViewModel() {

    private var liveData: LiveData<PagedList<FlickrPhoto>>? = null

    fun getPhotos(flickrApi: FlickrApi, query: String, resetData : Boolean = false) : LiveData<PagedList<FlickrPhoto>> {
        if (liveData == null || resetData) {
            val dataFactory = PhotoDataFactory(flickrApi, query)
            val config = PagedList.Config.Builder()
                    .setPageSize(100)
                    .setInitialLoadSizeHint(100)
                    .setEnablePlaceholders(false)
                    .build()

            liveData = LivePagedListBuilder<Int, FlickrPhoto>(dataFactory, config)
                    .setInitialLoadKey(1)
                    .build()
        }
        return liveData as LiveData<PagedList<FlickrPhoto>>
    }
}
