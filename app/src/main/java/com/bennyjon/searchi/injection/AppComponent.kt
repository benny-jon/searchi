package com.bennyjon.searchi.injection

import com.bennyjon.searchi.App
import com.bennyjon.searchi.ui.MainActivity
import com.bennyjon.searchi.injection.scopes.PerApp
import dagger.android.AndroidInjectionModule
import dagger.android.AndroidInjector

@PerApp
@dagger.Component(modules = [
    AppModule::class,
    AndroidInjectionModule::class,
    BuildersModule::class])
interface AppComponent : AndroidInjector<MainActivity> {

    fun inject(application: App)
}
