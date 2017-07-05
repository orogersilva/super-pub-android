package com.orogersilva.superpub.dublin.domain.repository

import io.reactivex.Flowable

/**
 * Created by orogersilva on 7/5/2017.
 */
interface UserRepository {

    // region METHODS

    fun getLastLocation(): Flowable<Pair<Double, Double>>

    // endregion
}