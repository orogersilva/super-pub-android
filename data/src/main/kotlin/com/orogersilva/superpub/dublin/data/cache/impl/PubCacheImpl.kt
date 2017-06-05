package com.orogersilva.superpub.dublin.data.cache.impl

import com.orogersilva.superpub.dublin.data.cache.PubCache
import com.orogersilva.superpub.dublin.data.entity.PubEntity
import com.orogersilva.superpub.dublin.data.shared.toImmutableMap
import com.orogersilva.superpub.dublin.domain.di.scope.LoggedInScope
import javax.inject.Inject

/**
 * Created by orogersilva on 5/28/2017.
 */
@LoggedInScope
class PubCacheImpl @Inject constructor(private val cache: MutableMap<Long, PubEntity>) : PubCache {

    // region OVERRIDED METHODS

    override fun getPubs(): List<PubEntity> = cache.toImmutableMap().values.toList()

    override fun savePub(pub: PubEntity) {

        cache.put(pub.id, pub)
    }

    override fun clear() {

        cache.clear()
    }

    // endregion
}