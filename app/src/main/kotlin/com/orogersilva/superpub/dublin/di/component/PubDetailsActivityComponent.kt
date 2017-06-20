package com.orogersilva.superpub.dublin.di.component

import com.orogersilva.superpub.dublin.di.module.PubDetailsPresenterModule
import com.orogersilva.superpub.dublin.domain.di.scope.ActivityScope
import com.orogersilva.superpub.dublin.presentation.screen.pubdetails.view.PubDetailsActivity
import dagger.Subcomponent

/**
 * Created by orogersilva on 6/19/2017.
 */
@ActivityScope
@Subcomponent(modules = arrayOf(
        PubDetailsPresenterModule::class
))
interface PubDetailsActivityComponent {

    // region INJECTORS

    fun inject(pubDetailsActivity: PubDetailsActivity)

    // endregion
}