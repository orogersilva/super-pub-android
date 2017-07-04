package com.orogersilva.superpub.dublin.domain.local

/**
 * Created by orogersilva on 7/4/2017.
 */
interface PreferencesDataSource {

    // region METHODS

    fun getLastLocation(): Pair<Double, Double>

    fun saveLocation(lat: Double, lng: Double)

    // endregion
}