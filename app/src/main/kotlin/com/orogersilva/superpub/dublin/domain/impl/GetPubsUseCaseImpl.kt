package com.orogersilva.superpub.dublin.domain.impl

import com.fernandocejas.frodo.annotation.RxLogObservable
import com.orogersilva.superpub.dublin.data.PubDataSource
import com.orogersilva.superpub.dublin.data.PubRepository
import com.orogersilva.superpub.dublin.domain.GetPubsUseCase
import com.orogersilva.superpub.dublin.model.Pub
import io.reactivex.Observable
import javax.inject.Inject

/**
 * Created by orogersilva on 5/17/2017.
 */
class GetPubsUseCaseImpl @Inject constructor(private val pubRepository: PubDataSource) : GetPubsUseCase {

    // region OVERRIDED METHODS

    @RxLogObservable
    override fun getPubs(lat: Double, lng: Double): Observable<Pub>? {

        if (!isValidLocation(lat, lng)) return Observable.error(IllegalArgumentException())

        return pubRepository.getPubs(fromLocation = "$lat,$lng")
    }

    // endregion

    // region UTILITY METHODS

    private fun isValidLocation(lat: Double, lng: Double): Boolean {

        val VALID_MIN_LAT = -90.0
        val VALID_MAX_LAT = 90.0
        val VALID_MIN_LNG = -180.0
        val VALID_MAX_LNG = 180.0

        return lat in VALID_MIN_LAT..VALID_MAX_LAT && lng in VALID_MIN_LNG..VALID_MAX_LNG
    }

    // endregion
}