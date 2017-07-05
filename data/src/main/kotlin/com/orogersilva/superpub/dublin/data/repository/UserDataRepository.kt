package com.orogersilva.superpub.dublin.data.repository

import com.orogersilva.superpub.dublin.domain.di.scope.ActivityScope
import com.orogersilva.superpub.dublin.domain.local.PreferencesDataSource
import com.orogersilva.superpub.dublin.domain.repository.UserRepository
import io.reactivex.Flowable
import javax.inject.Inject

/**
 * Created by orogersilva on 7/5/2017.
 */
@ActivityScope
class UserDataRepository @Inject constructor(private val userPreferencesDataSource: PreferencesDataSource) : UserRepository {

    // region DESTRUCTOR

    fun destroyInstance() {

        userPreferencesDataSource.clear()
    }

    // endregion

    // region OVERRIDED METHODS

    override fun getLastLocation(): Flowable<Pair<Double, Double>> = userPreferencesDataSource.getLastLocation()

    // endregion
}