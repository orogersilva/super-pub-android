package com.orogersilva.superpub.dublin.di.module

import com.orogersilva.superpub.dublin.domain.di.scope.ActivityScope
import com.orogersilva.superpub.dublin.domain.interactor.CalculateSuperPubRatingUseCase
import com.orogersilva.superpub.dublin.domain.interactor.impl.CalculateSuperPubRatingUseCaseImpl
import dagger.Module
import dagger.Provides

/**
 * Created by orogersilva on 6/13/2017.
 */
@ActivityScope
@Module
open class CalculateSuperPubRatingUseCaseModule {

    // region PROVIDERS

    @Provides @ActivityScope open fun provideCalculateSuperPubRatingUseCase():
            CalculateSuperPubRatingUseCase = CalculateSuperPubRatingUseCaseImpl()

    // endregion
}