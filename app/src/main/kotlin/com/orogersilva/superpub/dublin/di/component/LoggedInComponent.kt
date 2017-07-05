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
        DatabaseModule::class,
        GoogleApiModule::class,
        LocationSensorModule::class,
        NetworkModule::class,
        PreferencesModule::class,
        SchedulerProviderModule::class))
interface LoggedInComponent {

    // region FACTORY METHODS

    fun newPubsActivityComponent(getPubsUseCaseModule: GetPubsUseCaseModule,
                                 getLastLocationUseCaseModule: GetLastLocationUseCaseModule,
                                 calculateSuperPubRatingUseCaseModule: CalculateSuperPubRatingUseCaseModule,
                                 pubRepositoryModule: PubRepositoryModule,
                                 pubsAdapterModule: PubsAdapterModule,
                                 pubsLocationBroadcastReceiverModule: PubsLocationBroadcastReceiverModule,
                                 pubsPresenterModule: PubsPresenterModule): PubsActivityComponent

    fun newPubDetailsActivityComponent(pubDetailsPresenterModule: PubDetailsPresenterModule): PubDetailsActivityComponent

    // endregion
}