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
import com.orogersilva.superpub.dublin.domain.di.scope.ActivityScope
import com.orogersilva.superpub.dublin.domain.repository.PubRepository
import dagger.Module
import dagger.Provides
import io.realm.RealmConfiguration

/**
 * Created by orogersilva on 5/30/2017.
 */
@ActivityScope
@Module
open class PubRepositoryModule {

    // region PROVIDERS

    @Provides @ActivityScope open fun providePubDataRepository(pubCache: PubCache,
                                                               @Local pubLocalDataSource: PubDataSource?,
                                                               @Remote pubRemoteDataSource: PubDataSource?,
                                                               clock: Clock): PubRepository =
            PubDataRepository(pubCache, pubLocalDataSource, pubRemoteDataSource, clock)

    @Provides @ActivityScope @Local open fun providePubLocalDataSource(realmConfiguration: RealmConfiguration?): PubDataSource? = PubLocalDataSource(realmConfiguration)

    @Provides @ActivityScope @Remote open fun providePubRemoteDataSource(@AccessToken accessToken: String,
                                                                         baseEndpoint: String): PubDataSource? =
            PubRemoteDataSource(accessToken, RestClient.getApiClient(SearchApiClient::class.java, baseEndpoint))

    // endregion
}