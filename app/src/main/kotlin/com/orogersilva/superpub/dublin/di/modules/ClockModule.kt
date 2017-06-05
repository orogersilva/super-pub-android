package com.orogersilva.superpub.dublin.di.modules

import com.orogersilva.superpub.dublin.data.shared.date.Clock
import com.orogersilva.superpub.dublin.data.shared.date.impl.CustomClock
import com.orogersilva.superpub.dublin.domain.di.scope.PubInfoScope
import dagger.Module
import dagger.Provides

/**
 * Created by orogersilva on 5/29/2017.
 */
@PubInfoScope
@Module
open class ClockModule {

    // region PROVIDERS

    @Provides @PubInfoScope open fun provideClock(): Clock = CustomClock()

    // endregion
}