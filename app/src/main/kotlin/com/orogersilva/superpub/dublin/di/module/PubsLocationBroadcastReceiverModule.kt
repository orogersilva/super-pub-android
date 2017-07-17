package com.orogersilva.superpub.dublin.di.module

import com.orogersilva.superpub.dublin.device.location.LocationBroadcastReceiver
import com.orogersilva.superpub.dublin.domain.di.scope.ActivityScope
import com.orogersilva.superpub.dublin.data.PreferencesDataSource
import dagger.Module
import dagger.Provides

/**
 * Created by orogersilva on 7/4/2017.
 */
@ActivityScope
@Module
open class PubsLocationBroadcastReceiverModule {

    // region PROVIDERS

    @Provides @ActivityScope open fun provideLocationBroadcastReceiver(preferencesDataSource: PreferencesDataSource) =
            LocationBroadcastReceiver(preferencesDataSource)

    // endregion
}