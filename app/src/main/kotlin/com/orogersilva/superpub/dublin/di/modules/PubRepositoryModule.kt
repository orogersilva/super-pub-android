package com.orogersilva.superpub.dublin.di.modules

import android.content.Context
import com.orogersilva.superpub.dublin.data.Local
import com.orogersilva.superpub.dublin.data.PubDataSource
import com.orogersilva.superpub.dublin.data.local.PubLocalDataSource
import dagger.Module
import dagger.Provides
import io.realm.Realm
import io.realm.RealmConfiguration
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by orogersilva on 4/5/2017.
 */
@Module
class PubRepositoryModule(private val context: Context) {

    // region PROVIDERS

    @Provides fun provideRealm(): Realm? {

        Realm.init(context)

        val realmConfiguration = RealmConfiguration.Builder()
                .name("superpub.realm")
                .build()

        return Realm.getInstance(realmConfiguration)
    }

    @Singleton @Provides @Local fun providePubLocalDataSource(realm: Realm?): PubDataSource {

        return PubLocalDataSource(realm)
    }

    // endregion
}