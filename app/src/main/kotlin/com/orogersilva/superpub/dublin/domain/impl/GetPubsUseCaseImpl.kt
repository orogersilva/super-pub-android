package com.orogersilva.superpub.dublin.domain.impl

import com.orogersilva.superpub.dublin.data.PubRepository
import com.orogersilva.superpub.dublin.domain.GetPubsUseCase
import com.orogersilva.superpub.dublin.model.Pub
import io.reactivex.Observable
import javax.inject.Inject

/**
 * Created by orogersilva on 5/17/2017.
 */
class GetPubsUseCaseImpl @Inject constructor(private val pubRepository: PubRepository) : GetPubsUseCase {

    // region OVERRIDED METHODS

    override fun getPubs(center: String): Observable<Pub>? = pubRepository.getPubs(fromLocation = center)

    // endregion
}