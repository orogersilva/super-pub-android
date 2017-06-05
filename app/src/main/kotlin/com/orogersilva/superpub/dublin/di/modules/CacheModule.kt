package com.orogersilva.superpub.dublin.di.modules

import com.orogersilva.superpub.dublin.data.cache.PubCache
import com.orogersilva.superpub.dublin.data.cache.impl.PubCacheImpl
import com.orogersilva.superpub.dublin.data.entity.PubEntity
import com.orogersilva.superpub.dublin.domain.di.scope.LoggedInScope
import dagger.Module
import dagger.Provides

/**
 * Created by orogersilva on 5/29/2017.
 */
@LoggedInScope
@Module
open class CacheModule {

    // region PROVIDERS

    @Provides @LoggedInScope open fun providePubCache(): PubCache = PubCacheImpl(mutableMapOf<Long, PubEntity>())

    // endregion
}