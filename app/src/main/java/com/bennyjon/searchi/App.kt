package com.bennyjon.searchi

import android.app.Application
import androidx.paging.ExperimentalPagingApi
import com.bennyjon.searchi.injection.AppModule
import com.bennyjon.searchi.injection.DaggerAppComponent
import com.squareup.picasso.Picasso
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import javax.inject.Inject

@ExperimentalPagingApi
class App : Application(), HasAndroidInjector {

    @Inject
    internal lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Any>

    override fun onCreate() {
        super.onCreate()

        DaggerAppComponent.builder()
                .appModule(AppModule())
                .build()
                .inject(this)

        Picasso.get().isLoggingEnabled = true
    }

    override fun androidInjector(): AndroidInjector<Any> {
        return dispatchingAndroidInjector
    }
}
