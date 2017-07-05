package com.orogersilva.superpub.dublin.di.module

import android.content.Context
import android.content.SharedPreferences
import com.orogersilva.superpub.dublin.data.local.UserPreferencesDataSource
import com.orogersilva.superpub.dublin.domain.di.scope.LoggedInScope
import com.orogersilva.superpub.dublin.domain.local.PreferencesDataSource
import dagger.Module
import dagger.Provides

/**
 * Created by orogersilva on 7/4/2017.
 */
@LoggedInScope
@Module
open class PreferencesModule {

    // region PROPERTIES

    private val LOCATION_PREF_FILE_KEY = "com.orogersilva.superpub.dublin.LOCATION_PREF_FILE_KEY"

    private val LAT_PREF_KEY = "LAT_PREF_KEY"
    private val LNG_PREF_KEY = "LNG_PREF_KEY"

    // endregion

    // region PROVIDERS

    @Provides @LoggedInScope open fun provideSharedPreferences(context: Context): SharedPreferences =
            context.getSharedPreferences(LOCATION_PREF_FILE_KEY, Context.MODE_PRIVATE)

    @Provides @LoggedInScope open fun provideSharedPreferencesEditor(sharedPreferences: SharedPreferences): SharedPreferences.Editor =
            sharedPreferences.edit()

    @Provides @LoggedInScope open fun provideUserLocationCallback(): UserPreferencesDataSource.UserLocationCallback =
            UserPreferencesDataSource.UserLocationCallback(LAT_PREF_KEY, LNG_PREF_KEY)

    @Provides @LoggedInScope open fun provideUserPreferencesDataSource(sharedPreferences: SharedPreferences,
                                                                       sharedPreferencesEditor: SharedPreferences.Editor,
                                                                       userLocationCallback: UserPreferencesDataSource.UserLocationCallback): PreferencesDataSource =
            UserPreferencesDataSource(sharedPreferences, sharedPreferencesEditor, LAT_PREF_KEY, LNG_PREF_KEY, userLocationCallback)

    // endregion
}