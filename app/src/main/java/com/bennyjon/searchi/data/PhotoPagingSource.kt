package com.bennyjon.searchi.data

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.bennyjon.searchi.network.models.FlickrPhoto
import com.bennyjon.searchi.network.FlickrApi
import retrofit2.HttpException
import java.io.IOException

class PhotoPagingSource(private val flickrApi: FlickrApi, private val query: String) :
        PagingSource<Int, FlickrPhoto>() {

    override fun getRefreshKey(state: PagingState<Int, FlickrPhoto>): Int {
        return 0
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, FlickrPhoto> {
        try {
            val response = flickrApi.search(query, params.key ?: 0)
            if (!response.isSuccessful) {
                return LoadResult.Error(Exception())
            }
            if (response.body()?.photos?.total?.toInt() == 0) {
                return LoadResult.Page(emptyList(), null, null)
            }
            val previousPage = if (params.key == 1) null else params.key?.minus(1)
            return LoadResult.Page(response.body()?.photos?.photo
                    ?: error("Error Fetching Photos"), previousPage, params.key?.plus(1))
        } catch (exception: IOException) {
            return LoadResult.Error(exception)
        } catch (exception: HttpException) {
            return LoadResult.Error(exception)
        }
    }
}
