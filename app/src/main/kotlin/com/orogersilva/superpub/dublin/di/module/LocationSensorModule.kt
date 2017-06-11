package com.orogersilva.superpub.dublin.di.module

import com.google.android.gms.common.api.GoogleApiClient
import com.orogersilva.superpub.dublin.device.location.DeviceLocationSensor
import com.orogersilva.superpub.dublin.domain.di.scope.LoggedInScope
import com.orogersilva.superpub.dublin.domain.manager.LocationSensor
import dagger.Module
import dagger.Provides

/**
 * Created by orogersilva on 6/9/2017.
 */
@LoggedInScope
@Module
open class LocationSensorModule {

    // region PROVIDERS

    @Provides @LoggedInScope open fun provideDeviceLocationConnectionSubscriber(): DeviceLocationSensor.DeviceLocationConnectionSubscriber =
            DeviceLocationSensor.DeviceLocationConnectionSubscriber()

    @Provides @LoggedInScope open fun provideDeviceLocationListener(): DeviceLocationSensor.DeviceLocationListener =
            DeviceLocationSensor.DeviceLocationListener()

    @Provides @LoggedInScope open fun provideDeviceLocationSensor(
            googleApiClient: GoogleApiClient,
            deviceLocationConnectionSubscriber: DeviceLocationSensor.DeviceLocationConnectionSubscriber,
            deviceLocationListener: DeviceLocationSensor.DeviceLocationListener): LocationSensor<Pair<Double, Double>> =
            DeviceLocationSensor(googleApiClient, deviceLocationConnectionSubscriber, deviceLocationListener)

    // endregion
}