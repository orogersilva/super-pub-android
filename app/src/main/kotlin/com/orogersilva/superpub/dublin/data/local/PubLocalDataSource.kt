package com.orogersilva.superpub.dublin.data.local

import com.orogersilva.superpub.dublin.data.PubDataSource
import com.orogersilva.superpub.dublin.model.Pub
import io.reactivex.Observable
import io.realm.Realm
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by orogersilva on 3/31/2017.
 */
@Singleton
class PubLocalDataSource @Inject constructor(private var realm: Realm?) : PubDataSource {

    // region PUBLIC METHODS

    fun destroyInstance() {

        realm?.close()
        realm = null
    }

    // endregion

    // region OVERRIDED METHODS

    override fun getPubs(): Observable<List<Pub>> =
            Observable.just(realm?.copyFromRealm(realm?.where(Pub::class.java)?.findAll()))

    override fun savePubs(pubs: List<Pub>) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun deletePubs() {

        realm?.executeTransaction { realm?.deleteAll() }
    }

    // endregion
}