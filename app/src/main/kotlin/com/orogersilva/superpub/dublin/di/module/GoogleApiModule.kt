package com.orogersilva.superpub.dublin.di.module

import android.content.Context
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.location.LocationServices
import com.orogersilva.superpub.dublin.domain.di.scope.LoggedInScope
import dagger.Module
import dagger.Provides

/**
 * Created by orogersilva on 6/9/2017.
 */
@LoggedInScope
@Module
open class GoogleApiModule {

    // region PROVIDERS

    @Provides @LoggedInScope open fun provideGoogleApiClient(context: Context): GoogleApiClient =
            GoogleApiClient.Builder(context)
                    .addApi(LocationServices.API)
                    .build()

    // endregion
}