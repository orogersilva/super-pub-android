package com.orogersilva.superpub.dublin.data.cache

import com.orogersilva.superpub.dublin.data.entity.PubEntity

/**
 * Created by orogersilva on 5/28/2017.
 */
interface PubCache {

    // region METHODS

    fun getPubs(): List<PubEntity>

    fun savePub(pub: PubEntity)

    fun savePubs(pubs: List<PubEntity>)

    fun clear()

    // endregion
}