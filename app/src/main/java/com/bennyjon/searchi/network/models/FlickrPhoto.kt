package com.bennyjon.searchi.network.models

import com.bennyjon.searchi.network.getPhotoUrl

data class FlickrPhoto(
    val id: String,
    val owner: String,
    val secret: String,
    val server: String,
    val farm: Int,
    val title: String,
    val ispublic: Int,
    val isfriend: Int,
    val isfamily: Int
) {
    fun getImageUrl() = getPhotoUrl(farm, server, id, secret)
}
