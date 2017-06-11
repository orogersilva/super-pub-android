package com.orogersilva.superpub.dublin.di.component

import com.orogersilva.superpub.dublin.di.module.*
import com.orogersilva.superpub.dublin.domain.di.scope.LoggedInScope
import dagger.Subcomponent

/**
 * Created by orogersilva on 4/5/2017.
 */
@LoggedInScope
@Subcomponent(modules = arrayOf(
        CacheModule::class,
        ClockModule::class,
        DatabaseModule::class,
        GoogleApiModule::class,
        LocationSensorModule::class,
        NetworkModule::class,
        SchedulerProviderModule::class))
interface LoggedInComponent {

    // region FACTORY METHODS

    fun newPubsActivityComponent(getPubsUseCaseModule: GetPubsUseCaseModule,
                                 getLastLocationUseCaseModule: GetLastLocationUseCaseModule,
                                 pubRepositoryModule: PubRepositoryModule,
                                 pubsPresenterModule: PubsPresenterModule): PubsActivityComponent

    // endregion
}