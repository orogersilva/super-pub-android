package com.orogersilva.superpub.dublin.di.module

import com.orogersilva.superpub.dublin.adapter.facebook.FacebookHelper
import com.orogersilva.superpub.dublin.domain.di.scope.ActivityScope
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

    @Provides @ActivityScope open fun provideLoginPresenter(facebookAdapterService: FacebookHelper): LoginContract.Presenter =
            LoginPresenter(loginView, facebookAdapterService)

    // endregion
}