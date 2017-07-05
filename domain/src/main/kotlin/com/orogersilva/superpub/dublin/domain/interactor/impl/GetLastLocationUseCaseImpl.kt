package com.orogersilva.superpub.dublin.domain.interactor.impl

import com.orogersilva.superpub.dublin.domain.di.scope.ActivityScope
import com.orogersilva.superpub.dublin.domain.interactor.GetLastLocationUseCase
import com.orogersilva.superpub.dublin.domain.repository.UserRepository
import io.reactivex.Flowable
import javax.inject.Inject

/**
 * Created by orogersilva on 6/9/2017.
 */
@ActivityScope
class GetLastLocationUseCaseImpl @Inject constructor(private val userRepository: UserRepository) : GetLastLocationUseCase {

    // region OVERRIDED METHODS

    override fun getLastLocation(): Flowable<Pair<Double, Double>> = userRepository.getLastLocation()

    // endregion
}