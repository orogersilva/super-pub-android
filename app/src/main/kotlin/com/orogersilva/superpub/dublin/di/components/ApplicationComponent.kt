package com.orogersilva.superpub.dublin.di.components

import com.orogersilva.superpub.dublin.di.modules.FacebookSdkModule
import com.orogersilva.superpub.dublin.di.modules.LoginPresenterModule
import dagger.Component
import javax.inject.Singleton

/**
 * Created by orogersilva on 4/17/2017.
 */
@Singleton
@Component
interface ApplicationComponent {

    // region FACTORY METHODS

    fun newLoginComponent(loginPresenterModule: LoginPresenterModule,
                          facebookSdkModule: FacebookSdkModule): LoginComponent

    // endregion
}