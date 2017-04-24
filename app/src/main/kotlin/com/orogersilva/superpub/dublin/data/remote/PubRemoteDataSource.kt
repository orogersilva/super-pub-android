package com.orogersilva.superpub.dublin.data.remote

import com.orogersilva.superpub.dublin.data.PubDataSource
import com.orogersilva.superpub.dublin.model.Pub
import io.reactivex.Observable

/**
 * Created by orogersilva on 4/21/2017.
 */
class PubRemoteDataSource : PubDataSource {

    // region OVERRIDED METHODS

    override fun getPubs(): Observable<List<Pub>> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun savePubs(pubs: List<Pub>) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun deletePubs() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    // endregion
}