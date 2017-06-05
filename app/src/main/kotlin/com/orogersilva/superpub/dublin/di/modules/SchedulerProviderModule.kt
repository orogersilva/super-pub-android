package com.orogersilva.superpub.dublin.di.modules

import com.orogersilva.superpub.dublin.domain.di.scope.PubInfoScope
import com.orogersilva.superpub.dublin.scheduler.SchedulerProvider
import com.orogersilva.superpub.dublin.scheduler.impl.AppSchedulerProvider
import dagger.Module
import dagger.Provides

/**
 * Created by orogersilva on 5/30/2017.
 */
@PubInfoScope
@Module
open class SchedulerProviderModule {

    // region PROVIDERS

    @Provides @PubInfoScope open fun provideSchedulerProvider(): SchedulerProvider = AppSchedulerProvider()

    // endregion
}