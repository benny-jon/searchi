package com.bennyjon.searchi.network.models

data class FlickrSearchResponse(
    val photos: FlickrPhotoList,
    val stat: String
)
