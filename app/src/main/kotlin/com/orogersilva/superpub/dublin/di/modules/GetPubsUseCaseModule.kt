package com.orogersilva.superpub.dublin.di.modules

import com.orogersilva.superpub.dublin.domain.di.scope.PubInfoScope
import com.orogersilva.superpub.dublin.domain.interactor.GetPubsUseCase
import com.orogersilva.superpub.dublin.domain.interactor.impl.GetPubsUseCaseImpl
import com.orogersilva.superpub.dublin.domain.repository.PubRepository
import dagger.Module
import dagger.Provides

/**
 * Created by orogersilva on 5/30/2017.
 */
@PubInfoScope
@Module
open class GetPubsUseCaseModule {

    // region PROVIDERS

    @Provides @PubInfoScope open fun provideGetPubsUseCase(pubDataRepository: PubRepository): GetPubsUseCase =
            GetPubsUseCaseImpl(pubDataRepository)

    // endregion
}