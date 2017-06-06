package com.orogersilva.superpub.dublin.di.component

import com.orogersilva.superpub.dublin.di.module.PubsPresenterModule
import com.orogersilva.superpub.dublin.di.scope.ActivityScope
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