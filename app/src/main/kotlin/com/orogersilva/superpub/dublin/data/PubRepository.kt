package com.orogersilva.superpub.dublin.data

import com.fernandocejas.frodo.annotation.RxLogObservable
import com.orogersilva.superpub.dublin.di.scopes.PubInfoScope
import com.orogersilva.superpub.dublin.model.Pub
import com.orogersilva.superpub.dublin.shared.toImmutableMap
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

/**
 * Created by orogersilva on 4/27/2017.
 */
@PubInfoScope
open class PubRepository @Inject constructor(private @Local var pubLocalDataSource: PubDataSource?,
                                             private @Remote var pubRemoteDataSource: PubDataSource?) : PubDataSource {

    // region FIELDS

    internal var cachedPubs: MutableMap<Long, Pub>? = mutableMapOf()

    // endregion

    // region DESTRUCTOR

    fun destroyInstance() {

        pubLocalDataSource = null
        pubRemoteDataSource = null

        cachedPubs?.clear()
        cachedPubs = null
    }

    // endregion

    // region OVERRIDED METHODS

    @RxLogObservable
    override fun getPubs(query: String, type: String, fromLocation: String, radius: Int, limit: Int, fields: String, displayedDataTimestamp: Long): Observable<Pub>? =
            getMergedPubs(query, type, fromLocation, radius, limit, fields)
                    ?.filter { it.timestamp > displayedDataTimestamp }

    override fun savePubs(pubs: List<Pub>, deleteExistingPubs: Boolean) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    // endregion

    // region UTILITY METHODS

    @RxLogObservable
    internal fun getMergedPubs(query: String, type: String, center: String, radius: Int, limit: Int, fields: String): Observable<Pub>? =
            Observable.mergeDelayError(Observable.fromIterable(cachedPubs?.toImmutableMap()?.values?.toList()),
                    pubLocalDataSource?.getPubs(query, type, center, radius, limit, fields)
                            ?.doOnNext {
                                cacheInMemory(it)
                            }
                            ?.subscribeOn(Schedulers.io()),
                    pubRemoteDataSource?.getPubs(query, type, center, radius, limit, fields)
                            ?.doOnNext {
                                it.timestamp = System.currentTimeMillis()
                                cacheInMemory(it)
                            }
                            ?.doOnComplete {
                                saveToDisk(cachedPubs.toImmutableMap().values.toList())
                            }
                            ?.subscribeOn(Schedulers.io()))

    /*@RxLogObservable
    internal fun getMergedPubs(query: String, type: String, center: String, radius: Int, limit: Int, fields: String): Observable<Pub>? {

        val o1 = Observable.fromIterable(cachedPubs?.toImmutableMap()?.values?.toList())
                ?.doOnNext {
                    var i = 1
                    i++
                }
                ?.doOnError {
                    var i = 1
                    i++
                }
                ?.doOnComplete {
                    var i = 1
                    i++
                }
        val o2 = pubLocalDataSource?.getPubs(query, type, center, radius, limit, fields)
                ?.doOnNext {
                    cacheInMemory(it)
                }
                ?.doOnError {
                    var i = 1
                    i++
                }
                ?.doOnComplete {
                    var i = 1
                    i++
                }
                ?.subscribeOn(Schedulers.io())
        val o3 = pubRemoteDataSource?.getPubs(query, type, center, radius, limit, fields)
                ?.doOnNext {
                    it.timestamp = System.currentTimeMillis()
                    cacheInMemory(it)
                }
                ?.doOnError {
                    var i = 1
                    i++
                }
                ?.doOnComplete {
                    saveToDisk(cachedPubs.toImmutableMap().values.toList())
                }
                ?.subscribeOn(Schedulers.io())

        val o = Observable.mergeDelayError(o1, o2, o3)

        return o
    }*/

    internal fun cacheInMemory(pub: Pub) {

        if (cachedPubs == null) {
            cachedPubs = mutableMapOf()
        }

        cachedPubs?.put(pub.id, pub)
    }

    internal fun saveToDisk(pubs: List<Pub>) {

        pubLocalDataSource?.savePubs(pubs, true)
    }

    // endregion
}