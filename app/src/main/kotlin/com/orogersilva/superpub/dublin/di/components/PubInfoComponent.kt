package com.orogersilva.superpub.dublin.di.components

import com.orogersilva.superpub.dublin.di.modules.*
import com.orogersilva.superpub.dublin.domain.di.scope.PubInfoScope
import com.orogersilva.superpub.dublin.presentation.screen.pubs.view.PubsActivity
import dagger.Subcomponent

/**
 * Created by orogersilva on 4/5/2017.
 */
@PubInfoScope
@Subcomponent(modules = arrayOf(PubsPresenterModule::class, GetPubsUseCaseModule::class,
        SchedulerProviderModule::class, PubRepositoryModule::class, CacheModule::class,
        DatabaseModule::class, NetworkModule::class, ClockModule::class))
interface PubInfoComponent {

    // region INJECTORS

    fun inject(pubsActivity: PubsActivity)

    // endregion
}