package com.orogersilva.superpub.dublin.data.repository

import com.fernandocejas.frodo.annotation.RxLogObservable
import com.orogersilva.superpub.dublin.data.PubDataSource
import com.orogersilva.superpub.dublin.data.cache.PubCache
import com.orogersilva.superpub.dublin.data.di.qualifier.Local
import com.orogersilva.superpub.dublin.data.di.qualifier.Remote
import com.orogersilva.superpub.dublin.data.entity.mapper.PubEntityMapper
import com.orogersilva.superpub.dublin.domain.di.scope.ActivityScope
import com.orogersilva.superpub.dublin.domain.model.Pub
import com.orogersilva.superpub.dublin.domain.repository.PubRepository
import io.reactivex.Flowable
import javax.inject.Inject

/**
 * Created by orogersilva on 5/28/2017.
 */
@ActivityScope
class PubDataRepository @Inject constructor(private var pubCache: PubCache,
                                            private @Local var pubLocalDataSource: PubDataSource?,
                                            private @Remote var pubRemoteDataSource: PubDataSource?) : PubRepository {

    // region DESTRUCTOR

    fun destroyInstance() {

        pubCache.clear()

        pubLocalDataSource = null
        pubRemoteDataSource = null
    }

    // endregion

    // region OVERRIDED METHODS

    @RxLogObservable
    override fun getPubs(query: String, type: String, fromLocation: String, radius: Int,
                         limit: Int, fields: String, getDataFromRemote: Boolean): Flowable<Pub>? {

        if (getDataFromRemote) {

            pubCache.clear()
            pubLocalDataSource?.deletePubs()
        }

        return Flowable.concat(Flowable.just(pubCache.getPubs()),
                pubLocalDataSource?.getPubs(query, type, fromLocation, radius, limit, fields)
                        ?.doOnNext {
                            pubCache.savePubs(it)
                        },
                pubRemoteDataSource?.getPubs(query, type, fromLocation, radius, limit, fields)
                        ?.doOnNext {
                            pubCache.savePubs(it)
                            pubLocalDataSource?.savePubs(pubCache.getPubs())
                        })
                ?.filter { pubs -> pubs != null && !pubs.isEmpty() }
                ?.take(1)
                ?.flatMap { pubsEntityList -> Flowable.fromIterable(PubEntityMapper.transformPubEntityList(pubsEntityList)) }
    }

    override fun savePubs(pubs: List<Pub>) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    // endregion
}