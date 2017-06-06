package com.orogersilva.superpub.dublin.di.module

import com.orogersilva.superpub.dublin.BuildConfig
import com.orogersilva.superpub.dublin.domain.di.scope.LoggedInScope
import dagger.Module
import dagger.Provides

/**
 * Created by orogersilva on 5/1/2017.
 */
@LoggedInScope
@Module
open class NetworkModule {

    // region PROVIDERS

    @Provides @LoggedInScope open fun provideBaseEndpoint(): String = BuildConfig.FACEBOOK_GRAPH_API

    // endregion
}