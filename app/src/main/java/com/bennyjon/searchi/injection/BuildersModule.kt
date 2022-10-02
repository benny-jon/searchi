package com.bennyjon.searchi.injection

import androidx.paging.ExperimentalPagingApi
import com.bennyjon.searchi.ui.AndroidViewsActivity
import com.bennyjon.searchi.ui.MainActivity
import com.bennyjon.searchi.ui.compose.ComposeActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@ExperimentalPagingApi
@Module
abstract class BuildersModule {

    @ContributesAndroidInjector
    abstract fun bindMainActivity() : MainActivity

    @ContributesAndroidInjector
    abstract fun bindAndroidViewActivity(): AndroidViewsActivity

    @ContributesAndroidInjector
    abstract fun bindComposeActivity(): ComposeActivity
}
