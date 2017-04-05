package com.orogersilva.superpub.dublin.data

import com.orogersilva.superpub.dublin.model.Pub
import io.reactivex.Observable

/**
 * Created by orogersilva on 3/31/2017.
 */
interface PubDataSource {

    // region CRUD

    fun getPubs(): Observable<List<Pub>>
    fun deletePubs()

    // endregion
}