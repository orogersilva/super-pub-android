package com.orogersilva.superpub.dublin

import android.location.Location

/**
 * Created by orogersilva on 5/23/2017.
 */
interface LocationCallback {

    // region METHODS

    fun onLocationManagerConnected()
    fun onLocationChanged(newLocation: Location?)

    // endregion
}