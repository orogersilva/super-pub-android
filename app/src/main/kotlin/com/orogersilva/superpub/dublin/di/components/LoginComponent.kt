package com.orogersilva.superpub.dublin.di.components

import com.orogersilva.superpub.dublin.di.modules.FacebookSdkModule
import com.orogersilva.superpub.dublin.di.modules.LoginPresenterModule
import com.orogersilva.superpub.dublin.presentation.screen.login.view.LoginActivity
import dagger.Component
import javax.inject.Singleton

/**
 * Created by orogersilva on 4/14/2017.
 */
@Singleton
@Component(modules = arrayOf(LoginPresenterModule::class, FacebookSdkModule::class))
interface LoginComponent {

    // region INJECTORS

    fun inject(loginActivity: LoginActivity)

    // endregion
}