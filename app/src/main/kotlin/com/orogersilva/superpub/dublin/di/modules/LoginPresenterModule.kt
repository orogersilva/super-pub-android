package com.orogersilva.superpub.dublin.di.modules

import com.facebook.CallbackManager
import com.facebook.login.LoginManager
import com.orogersilva.superpub.dublin.presentation.screen.login.LoginContract
import com.orogersilva.superpub.dublin.presentation.screen.login.LoginPresenter
import dagger.Module
import dagger.Provides

/**
 * Created by orogersilva on 4/14/2017.
 */
@Module
class LoginPresenterModule(private val loginView: LoginContract.View) {

    // region PROVIDERS

    @Provides open fun provideLoginPresenter(loginManager: LoginManager,
                                        callbackManager: CallbackManager): LoginContract.Presenter =
            LoginPresenter(loginView, loginManager, callbackManager)

    // endregion
}