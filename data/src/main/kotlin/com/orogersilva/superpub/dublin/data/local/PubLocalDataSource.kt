package com.orogersilva.superpub.dublin.data.local

import com.orogersilva.superpub.dublin.data.PubDataSource
import com.orogersilva.superpub.dublin.data.entity.PubEntity
import com.orogersilva.superpub.dublin.domain.di.scope.LoggedInScope
import io.reactivex.Flowable
import io.realm.Realm
import io.realm.RealmConfiguration
import javax.inject.Inject

/**
 * Created by orogersilva on 5/28/2017.
 */
@LoggedInScope
class PubLocalDataSource @Inject constructor(private var realmConfiguration: RealmConfiguration?) : PubDataSource {

    // region OVERRIDED METHODS

    override fun getPubs(query: String, type: String, fromLocation: String, radius: Int, limit: Int,
                         fields: String, displayedDataTimestamp: Long): Flowable<List<PubEntity>> {

        val realm = Realm.getInstance(realmConfiguration)

        val pubEntityRealmResults = realm.copyFromRealm(realm.where(PubEntity::class.java).findAll())

        realm.close()

        if (pubEntityRealmResults.isEmpty()) return Flowable.empty()

        return Flowable.just(pubEntityRealmResults)
    }

    override fun savePubs(pubs: List<PubEntity>?) {

        val realm = Realm.getInstance(realmConfiguration)

        try {

            realm.executeTransaction { it.insertOrUpdate(pubs) }

        } finally {

            realm.let { it.close() }
        }
    }

    override fun deletePubs() {

        val realm = Realm.getInstance(realmConfiguration)

        try {

            realm.executeTransaction { it.deleteAll() }

        } finally {

            realm.let { it.close() }
        }
    }

    // endregion
}