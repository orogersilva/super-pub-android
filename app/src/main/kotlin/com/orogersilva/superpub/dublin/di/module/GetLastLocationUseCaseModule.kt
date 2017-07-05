package com.orogersilva.superpub.dublin.di.module

import com.orogersilva.superpub.dublin.device.location.LocationBroadcastReceiver
import com.orogersilva.superpub.dublin.domain.di.scope.ActivityScope
import com.orogersilva.superpub.dublin.domain.interactor.GetLastLocationUseCase
import com.orogersilva.superpub.dublin.domain.interactor.impl.GetLastLocationUseCaseImpl
import com.orogersilva.superpub.dublin.domain.local.PreferencesDataSource
import com.orogersilva.superpub.dublin.domain.manager.LocationSensor
import com.orogersilva.superpub.dublin.domain.repository.PubRepository
import com.orogersilva.superpub.dublin.domain.repository.UserRepository
import dagger.Module
import dagger.Provides

/**
 * Created by orogersilva on 6/9/2017.
 */
@ActivityScope
@Module
open class GetLastLocationUseCaseModule {

    // region PROVIDERS

    @Provides @ActivityScope open fun provideGetLastLocationUseCase(userRepository: UserRepository): GetLastLocationUseCase =
            GetLastLocationUseCaseImpl(userRepository)

    // endregion
}