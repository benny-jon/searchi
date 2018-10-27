package com.bennyjon.searchi.injection

import com.bennyjon.searchi.injection.scopes.PerApp
import com.bennyjon.searchi.network.FlickrApi
import com.bennyjon.searchi.network.getBaseUrl
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

@dagger.Module
class AppModule {

    @Provides
    @PerApp
    fun provideRetrofit() : Retrofit {
        return Retrofit.Builder()
                .baseUrl(getBaseUrl())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
    }

    @Provides
    @PerApp
    fun provideFlickrService(retrofit: Retrofit) : FlickrApi {
        return retrofit.create(FlickrApi::class.java)
    }
}
