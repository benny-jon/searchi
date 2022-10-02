package com.bennyjon.searchi.network.models

data class FlickrPhotoList(
    val page: Int,
    val pages: Int,
    val perpage: Int,
    val total: String,
    val photo: List<FlickrPhoto>
)
