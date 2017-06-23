package com.orogersilva.superpub.dublin.di.module

import com.facebook.CallbackManager
import com.facebook.login.LoginManager
import com.nhaarman.mockito_kotlin.mock
import com.orogersilva.superpub.dublin.domain.di.scope.ActivityScope
import com.orogersilva.superpub.dublin.presentation.screen.login.LoginContract
import dagger.Module
import dagger.Provides

/**
 * Created by orogersilva on 6/20/2017.
 */
class MockLoginPresenterModule(val viewMock: LoginContract.View,
                               val loginPresenterMock: LoginContract.Presenter) : LoginPresenterModule(viewMock) {

    // region OVERRIDED METHODS

    override fun provideLoginView(): LoginContract.View = viewMock

    override fun provideLoginPresenter(loginManager: LoginManager,
                                       callbackManager: CallbackManager): LoginContract.Presenter =
            loginPresenterMock

    // endregion
}