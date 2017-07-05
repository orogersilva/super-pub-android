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

    // region PROVIDERS

    @Provides @LoggedInScope open fun provideSharedPreferences(context: Context): SharedPreferences =
            context.getSharedPreferences("com.orogersilva.superpub.dublin.LOCATION_PREF_FILE_KEY", Context.MODE_PRIVATE)

    @Provides @LoggedInScope open fun provideSharedPreferencesEditor(sharedPreferences: SharedPreferences) = sharedPreferences.edit()

    @Provides @LoggedInScope open fun provideUserPreferencesDataSource(sharedPreferences: SharedPreferences,
                                                                       sharedPreferencesEditor: SharedPreferences.Editor): PreferencesDataSource =
            UserPreferencesDataSource(sharedPreferences, sharedPreferencesEditor, "LAT_PREF_KEY", "LNG_PREF_KEY")

    // endregion
}