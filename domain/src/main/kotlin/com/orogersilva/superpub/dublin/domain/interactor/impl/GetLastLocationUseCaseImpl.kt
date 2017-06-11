package com.orogersilva.superpub.dublin.domain.interactor.impl

import com.orogersilva.superpub.dublin.domain.di.scope.ActivityScope
import com.orogersilva.superpub.dublin.domain.interactor.GetLastLocationUseCase
import com.orogersilva.superpub.dublin.domain.manager.LocationSensor
import io.reactivex.Observable
import javax.inject.Inject

/**
 * Created by orogersilva on 6/9/2017.
 */
@ActivityScope
class GetLastLocationUseCaseImpl @Inject constructor(
        private val deviceLocationSensor: LocationSensor<Pair<Double, Double>>) : GetLastLocationUseCase {

    // region OVERRIDED METHODS

    override fun getLastLocation(): Observable<Pair<Double, Double>> {

        return deviceLocationSensor.getLastLocation()
    }

    // endregion
}