package com.bennyjon.searchi.data

import android.net.Uri

/**
 * Util class to avoid using network models directly in the UI layer.
 */
data class FlickrPhotoData(
    val id: String,
    val imageUri: Uri
) {
    val imageUrl = imageUri.toString()
}
