package com.bennyjon.searchi.injection

import com.bennyjon.searchi.ui.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class BuildersModule {

    @ContributesAndroidInjector
    abstract fun bindMainActivity() : MainActivity
}
