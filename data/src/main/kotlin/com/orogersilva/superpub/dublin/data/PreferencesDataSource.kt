package com.orogersilva.superpub.dublin.data

import android.app.PendingIntent
import com.google.android.gms.common.api.Status
import io.reactivex.Flowable

/**
 * Created by orogersilva on 7/4/2017.
 */
interface PreferencesDataSource {

    // region METHODS

    fun getLastLocation(): Flowable<Pair<Double, Double>>

    fun saveLocation(lat: Double, lng: Double)

    fun setLocationSettingsFailureStatus(status: Status)

    fun clear()

    // endregion
}