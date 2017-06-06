package com.orogersilva.superpub.dublin.di.module

import android.content.Context
import com.orogersilva.superpub.dublin.di.qualifier.DatabaseName
import com.orogersilva.superpub.dublin.domain.di.scope.LoggedInScope
import dagger.Module
import dagger.Provides
import io.realm.Realm
import io.realm.RealmConfiguration

/**
 * Created by orogersilva on 5/1/2017.
 */
@LoggedInScope
@Module
open class DatabaseModule(private val provideRealmInstance: Boolean) {

    // region PROVIDERS

    @Provides @LoggedInScope @DatabaseName open fun provideDatabaseName(): String = "superpub.realm"

    @Provides @LoggedInScope open fun provideRealmConfiguration(context: Context?, @DatabaseName databaseName: String): RealmConfiguration? {

        if (!provideRealmInstance) return null

        Realm.init(context)

        return RealmConfiguration.Builder()
                .name(databaseName)
                .build()
    }

    // endregion
}