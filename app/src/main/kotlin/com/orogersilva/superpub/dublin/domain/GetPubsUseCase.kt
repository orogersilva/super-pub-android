package com.orogersilva.superpub.dublin.domain

import com.orogersilva.superpub.dublin.model.Pub
import io.reactivex.Observable

/**
 * Created by orogersilva on 5/17/2017.
 */
interface GetPubsUseCase {

    // region METHODS

    fun getPubs(latitude: Double, longitude: Double): Observable<Pub>?

    // endregion
}