package com.orogersilva.superpub.dublin.domain.interactor

import io.reactivex.Observable

/**
 * Created by orogersilva on 6/9/2017.
 */
interface GetLastLocationUseCase {

    // region METHODS

    fun getLastLocation(): Observable<Pair<Double, Double>>

    // endregion
}