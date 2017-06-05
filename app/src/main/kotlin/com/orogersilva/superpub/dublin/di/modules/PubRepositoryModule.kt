package com.orogersilva.superpub.dublin.di.modules

import com.orogersilva.superpub.dublin.data.PubDataSource
import com.orogersilva.superpub.dublin.data.cache.PubCache
import com.orogersilva.superpub.dublin.data.di.scope.Local
import com.orogersilva.superpub.dublin.data.di.scope.Remote
import com.orogersilva.superpub.dublin.data.repository.PubDataRepository
import com.orogersilva.superpub.dublin.data.shared.date.Clock
import com.orogersilva.superpub.dublin.domain.di.scope.PubInfoScope
import com.orogersilva.superpub.dublin.domain.repository.PubRepository
import dagger.Module
import dagger.Provides

/**
 * Created by orogersilva on 5/30/2017.
 */
@PubInfoScope
@Module
open class PubRepositoryModule {

    // region PROVIDERS

    @Provides @PubInfoScope open fun providePubDataRepository(pubCache: PubCache,
                                                              @Local pubLocalDataSource: PubDataSource?,
                                                              @Remote pubRemoteDataSource: PubDataSource?,
                                                              clock: Clock): PubRepository =
            PubDataRepository(pubCache, pubLocalDataSource, pubRemoteDataSource, clock)

    // endregion
}