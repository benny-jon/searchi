package com.bennyjon.searchi

import android.app.Activity
import android.app.Application
import com.bennyjon.searchi.injection.AppModule
import com.bennyjon.searchi.injection.DaggerAppComponent
import com.squareup.leakcanary.LeakCanary
import com.squareup.picasso.Picasso
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import javax.inject.Inject

class App : Application(), HasActivityInjector {

    @Inject
    internal lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Activity>

    override fun onCreate() {
        super.onCreate()

        if (LeakCanary.isInAnalyzerProcess(this)) {
            return
        }

        LeakCanary.install(this)

        DaggerAppComponent.builder()
                .appModule(AppModule())
                .build()
                .inject(this)

        Picasso.get().isLoggingEnabled = true
    }

    override fun activityInjector(): DispatchingAndroidInjector<Activity> {
        return dispatchingAndroidInjector
    }
}
