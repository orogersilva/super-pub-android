package com.orogersilva.superpub.dublin.data.remote

import com.fernandocejas.frodo.annotation.RxLogObservable
import com.orogersilva.superpub.dublin.data.PubDataSource
import com.orogersilva.superpub.dublin.data.api.endpoint.SearchApiClient
import com.orogersilva.superpub.dublin.data.entity.PubEntity
import com.orogersilva.superpub.dublin.data.entity.mapper.PubEntityMapper
import com.orogersilva.superpub.dublin.domain.di.qualifier.AccessToken
import com.orogersilva.superpub.dublin.domain.di.scope.LoggedInScope
import io.reactivex.Observable
import javax.inject.Inject

/**
 * Created by orogersilva on 5/28/2017.
 */
@LoggedInScope
class PubRemoteDataSource @Inject constructor(private @AccessToken val accessToken: String?,
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
                        (data) -> Observable.fromIterable(PubEntityMapper.transformPubsDataList(data))
                    }

    override fun savePubs(pubs: List<PubEntity>?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    // endregion
}