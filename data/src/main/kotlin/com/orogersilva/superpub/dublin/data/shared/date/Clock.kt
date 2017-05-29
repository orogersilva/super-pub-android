package com.orogersilva.superpub.dublin.data.shared.date

/**
 * Created by orogersilva on 5/28/2017.
 */
interface Clock {

    // region METHODS

    fun getCurrentTimeMillis(): Long

    // endregion
}