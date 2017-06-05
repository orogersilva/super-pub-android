package com.orogersilva.superpub.dublin.data.remote

import com.fernandocejas.frodo.annotation.RxLogObservable
import com.orogersilva.superpub.dublin.data.PubDataSource
import com.orogersilva.superpub.dublin.data.api.endpoint.SearchApiClient
import com.orogersilva.superpub.dublin.data.entity.PubEntity
import com.orogersilva.superpub.dublin.data.entity.mapper.PubMapper
import com.orogersilva.superpub.dublin.domain.di.scope.AccessToken
import com.orogersilva.superpub.dublin.domain.di.scope.LoggedInScope
import io.reactivex.Observable
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by orogersilva on 5/28/2017.
 */
@LoggedInScope
class PubRemoteDataSource @Inject constructor(private @AccessToken val accessToken: String,
                                              private var apiClient: SearchApiClient?) : PubDataSource {

    // region DESTRUCTOR

    fun destroyInstance() {

        apiClient = null
    }

    // endregion

    // region OVERRIDED METHODS

    @RxLogObservable
    override fun getPubs(query: String, type: String, fromLocation: String, radius: Int, limit: Int,
                         fields: String, displayedDataTimestamp: Long): Observable<PubEntity>? =
            apiClient?.getPubs(query, type, fromLocation, radius, limit, fields, accessToken)
                    ?.flatMap {
                        pubHttpResponse -> Observable.fromIterable(PubMapper.transformPubsDataList(pubHttpResponse.data))
                    }

    override fun savePubs(pubs: List<PubEntity>?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    // endregion
}