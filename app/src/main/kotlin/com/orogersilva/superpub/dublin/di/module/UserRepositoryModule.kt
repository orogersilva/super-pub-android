package com.orogersilva.superpub.dublin.di.module

import com.orogersilva.superpub.dublin.data.repository.UserDataRepository
import com.orogersilva.superpub.dublin.domain.di.scope.ActivityScope
import com.orogersilva.superpub.dublin.data.PreferencesDataSource
import com.orogersilva.superpub.dublin.domain.repository.UserRepository
import dagger.Module
import dagger.Provides

/**
 * Created by orogersilva on 7/5/2017.
 */
@ActivityScope
@Module
open class UserRepositoryModule {

    // region PROVIDERS

    @Provides @ActivityScope open fun provideUserDataRepository(userPreferencesDataSource: PreferencesDataSource): UserRepository =
            UserDataRepository(userPreferencesDataSource)

    // endregion
}