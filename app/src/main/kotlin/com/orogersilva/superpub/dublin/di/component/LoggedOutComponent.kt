package com.orogersilva.superpub.dublin.di.component

import com.orogersilva.superpub.dublin.di.module.LoginPresenterModule
import com.orogersilva.superpub.dublin.domain.di.scope.LoggedOutScope
import dagger.Subcomponent

/**
 * Created by orogersilva on 4/14/2017.
 */
@LoggedOutScope
@Subcomponent
interface LoggedOutComponent {

    // region FACTORY METHODS

    fun newLoginActivityComponent(loginPresenterModule: LoginPresenterModule): LoginActiivtyComponent

    // endregion
}