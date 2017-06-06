package com.orogersilva.superpub.dublin.di.module

import com.orogersilva.superpub.dublin.domain.di.scope.LoggedInScope
import com.orogersilva.superpub.dublin.scheduler.SchedulerProvider
import com.orogersilva.superpub.dublin.scheduler.impl.AppSchedulerProvider
import dagger.Module
import dagger.Provides

/**
 * Created by orogersilva on 5/30/2017.
 */
@LoggedInScope
@Module
open class SchedulerProviderModule {

    // region PROVIDERS

    @Provides @LoggedInScope open fun provideSchedulerProvider(): SchedulerProvider = AppSchedulerProvider()

    // endregion
}