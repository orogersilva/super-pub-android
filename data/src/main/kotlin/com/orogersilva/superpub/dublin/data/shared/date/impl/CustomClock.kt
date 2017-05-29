package com.orogersilva.superpub.dublin.data.shared.date.impl

import com.orogersilva.superpub.dublin.data.shared.date.Clock

/**
 * Created by orogersilva on 5/28/2017.
 */
class CustomClock : Clock {

    // region OVERRIDED METHODS

    override fun getCurrentTimeMillis(): Long = System.currentTimeMillis()

    // endregion
}