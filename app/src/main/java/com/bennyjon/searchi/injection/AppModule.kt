package com.bennyjon.searchi.injection

import com.bennyjon.searchi.injection.scopes.PerApp
import com.bennyjon.searchi.network.FlickrApi
import com.bennyjon.searchi.network.QueryParam
import com.bennyjon.searchi.network.getBaseUrl
import com.bennyjon.searchi.network.getFlickApiKey
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

@dagger.Module
class AppModule {

    @PerApp
    @Provides
    fun provideOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor { chain ->
                val originalRequest = chain.request()
                val originalUrl = originalRequest.url

                val newUrl = originalUrl.newBuilder()
                    .addQueryParameter(QueryParam.API_KEY, getFlickApiKey())
                    .build()

                val requestBuilder = originalRequest.newBuilder()
                    .url(newUrl)

                chain.proceed(requestBuilder.build())
            }.build()
    }

    @Provides
    @PerApp
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(getBaseUrl())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
    }

    @Provides
    @PerApp
    fun provideFlickrService(retrofit: Retrofit): FlickrApi {
        return retrofit.create(FlickrApi::class.java)
    }
}
