package com.orogersilva.superpub.dublin.domain.manager

import io.reactivex.Flowable

/**
 * Created by orogersilva on 6/9/2017.
 */
interface LocationSensor<T> {

    // region METHODS

    fun getLastLocation(): Flowable<T>

    // endregion
}