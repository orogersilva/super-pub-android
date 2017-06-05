package com.orogersilva.superpub.dublin.di.modules

import com.orogersilva.superpub.dublin.domain.di.scope.PubInfoScope
import com.orogersilva.superpub.dublin.domain.interactor.GetPubsUseCase
import com.orogersilva.superpub.dublin.presentation.screen.pubs.PubsContract
import com.orogersilva.superpub.dublin.presentation.screen.pubs.PubsPresenter
import com.orogersilva.superpub.dublin.scheduler.SchedulerProvider
import dagger.Module
import dagger.Provides

/**
 * Created by orogersilva on 5/30/2017.
 */
@PubInfoScope
@Module
open class PubsPresenterModule(private val pubsView: PubsContract.View) {

    // region PROVIDERS

    @Provides @PubInfoScope open fun providePubsView() = pubsView

    @Provides @PubInfoScope open fun providePubsPresenter(getPubsUseCase: GetPubsUseCase, schedulerProvider: SchedulerProvider): PubsContract.Presenter =
            PubsPresenter(pubsView, getPubsUseCase, schedulerProvider)

    // endregion
}