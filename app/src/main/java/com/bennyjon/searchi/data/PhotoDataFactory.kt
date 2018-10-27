package com.bennyjon.searchi.data

import android.arch.paging.DataSource
import com.bennyjon.searchi.models.FlickrPhoto
import com.bennyjon.searchi.network.FlickrApi

class PhotoDataFactory(private val flickrApi: FlickrApi,
                       private val query: String) : DataSource.Factory<Int, FlickrPhoto>() {

    override fun create(): DataSource<Int, FlickrPhoto> {
        return PhotoDataSource(flickrApi, query)
    }
}
