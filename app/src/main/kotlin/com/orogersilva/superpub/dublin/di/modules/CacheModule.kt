package com.orogersilva.superpub.dublin.di.modules

import com.orogersilva.superpub.dublin.data.cache.PubCache
import com.orogersilva.superpub.dublin.data.cache.impl.PubCacheImpl
import com.orogersilva.superpub.dublin.data.entity.PubEntity
import com.orogersilva.superpub.dublin.domain.di.scope.PubInfoScope
import dagger.Module
import dagger.Provides

/**
 * Created by orogersilva on 5/29/2017.
 */
@PubInfoScope
@Module
open class CacheModule {

    // region PROVIDERS

    @Provides @PubInfoScope open fun providePubCache(): PubCache = PubCacheImpl(mutableMapOf<Long, PubEntity>())

    // endregion
}