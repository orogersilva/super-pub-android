package com.orogersilva.superpub.dublin.di.components

import com.orogersilva.superpub.dublin.di.modules.PubRepositoryModule
import dagger.Component
import javax.inject.Singleton

/**
 * Created by orogersilva on 4/5/2017.
 */
@Singleton
@Component(modules = arrayOf(PubRepositoryModule::class))
interface PubRepositoryComponent {

    // region INJECTORS



    // endregion
}