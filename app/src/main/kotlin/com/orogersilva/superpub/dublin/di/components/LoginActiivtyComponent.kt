package com.orogersilva.superpub.dublin.di.components

import com.orogersilva.superpub.dublin.di.modules.LoginPresenterModule
import com.orogersilva.superpub.dublin.di.scopes.ActivityScope
import com.orogersilva.superpub.dublin.presentation.screen.login.view.LoginActivity
import dagger.Subcomponent

/**
 * Created by orogersilva on 6/5/2017.
 */
@ActivityScope
@Subcomponent(modules = arrayOf(LoginPresenterModule::class))
interface LoginActiivtyComponent {

    // region INJECTORS

    fun inject(loginActivity: LoginActivity)

    // endregion
}