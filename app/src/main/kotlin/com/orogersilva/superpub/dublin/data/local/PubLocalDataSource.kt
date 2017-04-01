package com.orogersilva.superpub.dublin.data.local

import com.orogersilva.superpub.dublin.data.PubDataSource
import com.orogersilva.superpub.dublin.model.Pub
import io.reactivex.Observable
import io.realm.Realm

/**
 * Created by orogersilva on 3/31/2017.
 */
object PubLocalDataSource : PubDataSource {

    // region PROPERTIES

    private var realm: Realm? = null

    // endregion

    // region INITIALIZER / DESTRUCTOR

    fun getInstance(realm: Realm): PubDataSource {

        if (this.realm == null) {
            this.realm = realm
        }

        return this
    }

    fun destroyInstance() {

        realm?.close()
        realm = null
    }

    // endregion

    // region OVERRIDED METHODS

    override fun getPubs(): Observable<List<Pub>> =
            Observable.just(realm?.copyFromRealm(realm?.where(Pub::class.java)?.findAll()))

    // endregion
}