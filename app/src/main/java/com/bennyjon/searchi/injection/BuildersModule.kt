package com.bennyjon.searchi.injection

import androidx.paging.ExperimentalPagingApi
import com.bennyjon.searchi.ui.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@ExperimentalPagingApi
@Module
abstract class BuildersModule {

    @ContributesAndroidInjector
    abstract fun bindMainActivity() : MainActivity
}
