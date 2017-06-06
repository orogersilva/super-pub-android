package com.orogersilva.superpub.dublin.di.module

import com.orogersilva.superpub.dublin.data.shared.date.Clock
import com.orogersilva.superpub.dublin.data.shared.date.impl.CustomClock
import com.orogersilva.superpub.dublin.domain.di.scope.LoggedInScope
import dagger.Module
import dagger.Provides

/**
 * Created by orogersilva on 5/29/2017.
 */
@LoggedInScope
@Module
open class ClockModule {

    // region PROVIDERS

    @Provides @LoggedInScope open fun provideClock(): Clock = CustomClock()

    // endregion
}