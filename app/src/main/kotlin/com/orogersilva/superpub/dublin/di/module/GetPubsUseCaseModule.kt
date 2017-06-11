package com.orogersilva.superpub.dublin.di.module

import com.orogersilva.superpub.dublin.domain.di.scope.ActivityScope
import com.orogersilva.superpub.dublin.domain.di.scope.LoggedInScope
import com.orogersilva.superpub.dublin.domain.interactor.GetPubsUseCase
import com.orogersilva.superpub.dublin.domain.interactor.impl.GetPubsUseCaseImpl
import com.orogersilva.superpub.dublin.domain.repository.PubRepository
import dagger.Module
import dagger.Provides

/**
 * Created by orogersilva on 5/30/2017.
 */
@ActivityScope
@Module
open class GetPubsUseCaseModule {

    // region PROVIDERS

    @Provides @ActivityScope open fun provideGetPubsUseCase(pubDataRepository: PubRepository): GetPubsUseCase =
            GetPubsUseCaseImpl(pubDataRepository)

    // endregion
}