package com.orogersilva.superpub.dublin.domain.interactor.impl

import com.orogersilva.superpub.dublin.domain.di.scope.PubInfoScope
import com.orogersilva.superpub.dublin.domain.interactor.GetPubsUseCase
import com.orogersilva.superpub.dublin.domain.model.Pub
import com.orogersilva.superpub.dublin.domain.repository.PubRepository
import io.reactivex.Observable
import javax.inject.Inject

/**
 * Created by orogersilva on 5/26/2017.
 */
@PubInfoScope
class GetPubsUseCaseImpl @Inject constructor(private val pubRepository: PubRepository) : GetPubsUseCase {

    // region OVERRIDED METHODS

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