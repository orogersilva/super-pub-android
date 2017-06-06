package com.orogersilva.superpub.dublin.di.module

import com.orogersilva.superpub.dublin.data.PubDataSource
import com.orogersilva.superpub.dublin.data.api.RestClient
import com.orogersilva.superpub.dublin.data.api.endpoint.SearchApiClient
import com.orogersilva.superpub.dublin.data.cache.PubCache
import com.orogersilva.superpub.dublin.data.di.qualifier.Local
import com.orogersilva.superpub.dublin.data.di.qualifier.Remote
import com.orogersilva.superpub.dublin.data.local.PubLocalDataSource
import com.orogersilva.superpub.dublin.data.remote.PubRemoteDataSource
import com.orogersilva.superpub.dublin.data.repository.PubDataRepository
import com.orogersilva.superpub.dublin.data.shared.date.Clock
import com.orogersilva.superpub.dublin.domain.di.qualifier.AccessToken
import com.orogersilva.superpub.dublin.domain.di.scope.LoggedInScope
import com.orogersilva.superpub.dublin.domain.repository.PubRepository
import dagger.Module
import dagger.Provides
import io.realm.RealmConfiguration

/**
 * Created by orogersilva on 5/30/2017.
 */
@LoggedInScope
@Module
open class PubRepositoryModule {

    // region PROVIDERS

    @Provides @LoggedInScope open fun providePubDataRepository(pubCache: PubCache,
                                                               @Local pubLocalDataSource: PubDataSource?,
                                                               @Remote pubRemoteDataSource: PubDataSource?,
                                                               clock: Clock): PubRepository =
            PubDataRepository(pubCache, pubLocalDataSource, pubRemoteDataSource, clock)

    @Provides @LoggedInScope @Local open fun providePubLocalDataSource(realmConfiguration: RealmConfiguration?): PubDataSource? = PubLocalDataSource(realmConfiguration)

    @Provides @LoggedInScope @Remote open fun providePubRemoteDataSource(@AccessToken accessToken: String,
                                                                                                                           baseEndpoint: String): PubDataSource? =
            PubRemoteDataSource(accessToken, RestClient.getApiClient(SearchApiClient::class.java, baseEndpoint))

    // endregion
}