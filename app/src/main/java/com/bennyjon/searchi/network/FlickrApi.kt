package com.bennyjon.searchi.network

import android.net.Uri
import com.bennyjon.searchi.models.FlickrSearchResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface FlickrApi {

    @GET("services/rest/?method=flickr.photos.search&format=json&nojsoncallback=1")
    suspend fun search(
            @Query("text") input: String,
            @Query("page") page: Int = 0,
            @Query("api_key") apiKey: String = getFlickApiKey()): Response<FlickrSearchResponse>
}

/**
 * Helper method to build the Flick photo URL base.
 */
fun getPhotoUrl(farm: Int, server: String, id: String, secret: String): Uri {
    return Uri.parse(String.format("http://farm%d.static.flickr.com/%s/%s_%s.jpg", farm, server, id, secret))
}

/**
 * Returns the base URL of the Flick Api.
 */
fun getBaseUrl() : String {
    return "https://api.flickr.com"
}

/**
 * Returns the API KEY used for authentication with the Flickr API.
 */
fun getFlickApiKey() : String {
    throw NotImplementedError("Paste your Flick API key below and remove this exception.")
    return "PASTE_YOUR_FLICKR_API_KEY_HERE"
}
