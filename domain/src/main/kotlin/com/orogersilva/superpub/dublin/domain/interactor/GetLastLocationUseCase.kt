package com.orogersilva.superpub.dublin.domain.interactor

import io.reactivex.Flowable

/**
 * Created by orogersilva on 6/9/2017.
 */
interface GetLastLocationUseCase {

    // region METHODS

    fun getLastLocation(): Flowable<Pair<Double, Double>>

    // endregion
}