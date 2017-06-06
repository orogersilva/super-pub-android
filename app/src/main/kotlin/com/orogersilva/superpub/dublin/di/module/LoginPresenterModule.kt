package com.orogersilva.superpub.dublin.di.module

import com.facebook.CallbackManager
import com.facebook.login.LoginManager
import com.orogersilva.superpub.dublin.di.scope.ActivityScope
import com.orogersilva.superpub.dublin.presentation.screen.login.LoginContract
import com.orogersilva.superpub.dublin.presentation.screen.login.LoginPresenter
import dagger.Module
import dagger.Provides

/**
 * Created by orogersilva on 4/14/2017.
 */
@ActivityScope
@Module
open class LoginPresenterModule(private val loginView: LoginContract.View) {

    // region PROVIDERS

    @Provides @ActivityScope open fun provideLoginView() = loginView

    @Provides @ActivityScope open fun provideLoginPresenter(loginManager: LoginManager,
                                                            callbackManager: CallbackManager): LoginContract.Presenter =
            LoginPresenter(loginView, loginManager, callbackManager)

    // endregion
}