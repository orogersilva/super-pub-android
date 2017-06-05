package com.orogersilva.superpub.dublin.data.local

import com.fernandocejas.frodo.annotation.RxLogObservable
import com.orogersilva.superpub.dublin.data.PubDataSource
import com.orogersilva.superpub.dublin.data.entity.PubEntity
import com.orogersilva.superpub.dublin.domain.di.scope.LoggedInScope
import io.reactivex.Observable
import io.realm.Realm
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by orogersilva on 5/28/2017.
 */
@LoggedInScope
class PubLocalDataSource @Inject constructor(private var realm: Realm?) : PubDataSource {

    // region DESTRUCTOR

    fun destroyInstance() {

        realm?.close()
        realm = null
    }

    // endregion

    // region OVERRIDED METHODS

    @RxLogObservable
    override fun getPubs(query: String, type: String, fromLocation: String, radius: Int, limit: Int,
                         fields: String, displayedDataTimestamp: Long): Observable<PubEntity>? =
            Observable.just(realm?.copyFromRealm(realm?.where(PubEntity::class.java)?.findAll()))
                    .flatMap { Observable.fromIterable(it) }

    override fun savePubs(pubs: List<PubEntity>?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    // endregion

    // region UTILITY METHODS

    fun deletePubs() {

        realm?.executeTransaction { realm?.deleteAll() }
    }

    // endregion
}