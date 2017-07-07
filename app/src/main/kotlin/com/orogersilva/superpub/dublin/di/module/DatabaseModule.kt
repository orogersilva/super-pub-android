package com.orogersilva.superpub.dublin.di.module

import android.content.Context
import com.orogersilva.superpub.dublin.BuildConfig
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

    @Provides @LoggedInScope @DatabaseName open fun provideDatabaseName(): String = "superpub.db"

    @Provides @LoggedInScope open fun provideRealmConfiguration(context: Context, @DatabaseName databaseName: String): RealmConfiguration? {

        if (!provideRealmInstance) return null

        Realm.init(context)

        if (BuildConfig.DEBUG) {

            return RealmConfiguration.Builder()
                    .name(databaseName)
                    .deleteRealmIfMigrationNeeded()
                    .build()
        }

        return RealmConfiguration.Builder()
                .name(databaseName)
                .schemaVersion(com.orogersilva.superpub.dublin.data.BuildConfig.DATABASE_SCHEMA_VERSION)
                .build()
    }

    // endregion
}