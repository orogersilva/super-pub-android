package com.orogersilva.superpub.dublin.data.repository

import com.fernandocejas.frodo.annotation.RxLogObservable
import com.orogersilva.superpub.dublin.data.PubDataSource
import com.orogersilva.superpub.dublin.data.cache.PubCache
import com.orogersilva.superpub.dublin.data.di.qualifier.Local
import com.orogersilva.superpub.dublin.data.di.qualifier.Remote
import com.orogersilva.superpub.dublin.data.entity.PubEntity
import com.orogersilva.superpub.dublin.data.entity.mapper.PubMapper
import com.orogersilva.superpub.dublin.data.shared.date.Clock
import com.orogersilva.superpub.dublin.domain.di.scope.ActivityScope
import com.orogersilva.superpub.dublin.domain.model.Pub
import com.orogersilva.superpub.dublin.domain.repository.PubRepository
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

/**
 * Created by orogersilva on 5/28/2017.
 */
@ActivityScope
class PubDataRepository @Inject constructor(private var pubCache: PubCache?,
                                            private @Local var pubLocalDataSource: PubDataSource?,
                                            private @Remote var pubRemoteDataSource: PubDataSource?,
                                            private val clock: Clock) : PubRepository {

    // region DESTRUCTOR

    fun destroyInstance() {

        pubCache?.clear()
        pubCache = null

        pubLocalDataSource = null
        pubRemoteDataSource = null
    }

    // endregion

    // region OVERRIDED METHODS

    @RxLogObservable
    override fun getPubs(query: String, type: String, fromLocation: String, radius: Int, limit: Int, fields: String, displayedDataTimestamp: Long): Observable<Pub>?  =
            getMergedPubs(query, type, fromLocation, radius, limit, fields)
                    ?.map { pubEntity -> PubMapper.transform(pubEntity) }
                    ?.filter { it.timestamp > displayedDataTimestamp }

    override fun savePubs(pubs: List<Pub>) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    // endregion

    // region UTILITY METHODS

    @RxLogObservable
    internal fun getMergedPubs(query: String, type: String, center: String, radius: Int, limit: Int, fields: String): Observable<PubEntity>? =
            Observable.mergeDelayError(Observable.fromIterable(pubCache?.getPubs()),
                    pubLocalDataSource?.getPubs(query, type, center, radius, limit, fields)
                            ?.doOnNext {
                                pubCache?.savePub(it)
                            }
                            ?.subscribeOn(Schedulers.io()),
                    pubRemoteDataSource?.getPubs(query, type, center, radius, limit, fields)
                            ?.doOnNext {
                                it.timestamp = clock.getCurrentTimeMillis()
                                pubCache?.savePub(it)
                            }
                            ?.doOnComplete {
                                pubLocalDataSource?.savePubs(pubCache?.getPubs())
                            }
                            ?.subscribeOn(Schedulers.io()))

    // endregion
}