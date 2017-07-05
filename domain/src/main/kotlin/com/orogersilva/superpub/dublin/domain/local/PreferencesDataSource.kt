package com.orogersilva.superpub.dublin.domain.local

import io.reactivex.Flowable

/**
 * Created by orogersilva on 7/4/2017.
 */
interface PreferencesDataSource {

    // region METHODS

    fun getLastLocation(): Flowable<Pair<Double, Double>>

    fun saveLocation(lat: Double, lng: Double)

    fun clear()

    // endregion
}