package com.orogersilva.superpub.dublin.di.component

import com.orogersilva.superpub.dublin.di.module.*
import dagger.Component
import javax.inject.Singleton

/**
 * Created by orogersilva on 4/17/2017.
 */
@Singleton
@Component(modules = arrayOf(
        ApplicationModule::class,
        FacebookSdkModule::class))
interface ApplicationComponent {

    // region FACTORY METHODS

    fun newLoggedOutComponent(): LoggedOutComponent
    
    fun newLoggedinComponent(cacheModule: CacheModule,
                             databaseModule: DatabaseModule,
                             googleApiModule: GoogleApiModule,
                             locationSensorModule: LocationSensorModule,
                             networkModule: NetworkModule,
                             schedulerProviderModule: SchedulerProviderModule): LoggedInComponent

    // endregion
}