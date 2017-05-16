package com.orogersilva.superpub.dublin.di.components

import com.orogersilva.superpub.dublin.data.PubRepository
import com.orogersilva.superpub.dublin.di.modules.DatabaseModule
import com.orogersilva.superpub.dublin.di.modules.NetworkModule
import com.orogersilva.superpub.dublin.di.scopes.PubInfoScope
import dagger.Component
import dagger.Subcomponent
import javax.inject.Singleton

/**
 * Created by orogersilva on 4/5/2017.
 */
@PubInfoScope
@Subcomponent(modules = arrayOf(DatabaseModule::class, NetworkModule::class))
interface PubRepositoryComponent {

    // region PUBLIC METHODS

    fun getPubRepository(): PubRepository

    // endregion
}