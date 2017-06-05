package com.orogersilva.superpub.dublin.di.modules

import android.content.Context
import com.orogersilva.superpub.dublin.data.PubDataSource
import com.orogersilva.superpub.dublin.data.di.scope.Local
import com.orogersilva.superpub.dublin.data.local.PubLocalDataSource
import com.orogersilva.superpub.dublin.domain.di.scope.PubInfoScope
import dagger.Module
import dagger.Provides
import io.realm.Realm
import io.realm.RealmConfiguration

/**
 * Created by orogersilva on 5/1/2017.
 */
@PubInfoScope
@Module
open class DatabaseModule(private val provideRealmInstance: Boolean) {

    // region PROVIDERS

    @Provides @PubInfoScope open fun provideRealm(context: Context?): Realm? {

        if (!provideRealmInstance) return null

        Realm.init(context)

        val realmConfiguration = RealmConfiguration.Builder()
                .name("superpub.realm")
                .build()

        return Realm.getInstance(realmConfiguration)
    }

    @Provides @PubInfoScope @Local open fun providePubLocalDataSource(realm: Realm?): PubDataSource? = PubLocalDataSource(realm)

    // endregion
}