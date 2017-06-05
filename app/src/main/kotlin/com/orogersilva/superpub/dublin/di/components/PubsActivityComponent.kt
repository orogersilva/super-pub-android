package com.orogersilva.superpub.dublin.di.components

import com.orogersilva.superpub.dublin.di.modules.GetPubsUseCaseModule
import com.orogersilva.superpub.dublin.di.modules.PubRepositoryModule
import com.orogersilva.superpub.dublin.di.modules.PubsPresenterModule
import com.orogersilva.superpub.dublin.di.scopes.ActivityScope
import com.orogersilva.superpub.dublin.presentation.screen.pubs.view.PubsActivity
import dagger.Subcomponent

/**
 * Created by orogersilva on 6/5/2017.
 */
@ActivityScope
@Subcomponent(modules = arrayOf(PubsPresenterModule::class))
interface PubsActivityComponent {

    // region INJECTORS

    fun inject(pubsActivity: PubsActivity)

    // endregion
}