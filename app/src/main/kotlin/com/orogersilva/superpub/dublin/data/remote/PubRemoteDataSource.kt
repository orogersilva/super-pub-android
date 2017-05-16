package com.orogersilva.superpub.dublin.data.remote

import com.fernandocejas.frodo.annotation.RxLogObservable
import com.orogersilva.superpub.dublin.api.RestClient
import com.orogersilva.superpub.dublin.api.endpoint.SearchingApiClient
import com.orogersilva.superpub.dublin.data.PubDataSource
import com.orogersilva.superpub.dublin.model.Pub
import com.orogersilva.superpub.dublin.model.PubHttpResponse
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Timed
import org.reactivestreams.Subscriber
import org.reactivestreams.Subscription
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by orogersilva on 4/21/2017.
 */
@Singleton
class PubRemoteDataSource @Inject constructor(private var apiClient: SearchingApiClient?) : PubDataSource {

    // region DESTRUCTOR

    fun destroyInstance() {

        apiClient = null
    }

    // endregion

    // region OVERRIDED METHODS

    @RxLogObservable
    override fun getPubs(query: String, type: String, center: String, radius: Int, limit: Int,
                         fields: String, displayedDataTimestamp: Long): Observable<Pub>? =
            apiClient?.getPubs(query, type, center, radius, limit, fields)
                ?.flatMap { pubHttpResponse -> Observable.fromIterable(pubHttpResponse.data) }

    override fun savePubs(pubs: List<Pub>, deleteExistingPubs: Boolean) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    // endregion
}