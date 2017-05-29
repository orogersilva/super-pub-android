package com.orogersilva.superpub.dublin.device.location

import android.location.Location

/**
 * Created by orogersilva on 5/26/2017.
 */
interface LocationCallback {

    // region METHODS

    fun onLocationManagerConnected()

    fun onLocationChanged(newLocation: Location?)

    // endregion
}