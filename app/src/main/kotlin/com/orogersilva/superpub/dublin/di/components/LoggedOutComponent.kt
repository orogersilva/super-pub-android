package com.orogersilva.superpub.dublin.di.components

import com.orogersilva.superpub.dublin.di.modules.LoginPresenterModule
import com.orogersilva.superpub.dublin.domain.di.scope.LoggedOutScope
import com.orogersilva.superpub.dublin.presentation.screen.login.view.LoginActivity
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