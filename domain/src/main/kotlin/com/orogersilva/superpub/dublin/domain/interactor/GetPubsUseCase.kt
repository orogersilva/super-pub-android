package com.orogersilva.superpub.dublin.domain.interactor

import com.orogersilva.superpub.dublin.domain.model.Pub
import io.reactivex.Flowable
import org.reactivestreams.Publisher

/**
 * Created by orogersilva on 5/26/2017.
 */
interface GetPubsUseCase {

    // region METHODS

    fun getPubs(lat: Double, lng: Double, getNewestPubs: Boolean = true): Flowable<Pub>

    // endregion
}