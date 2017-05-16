package com.orogersilva.superpub.dublin.di.modules

import com.facebook.CallbackManager
import com.facebook.login.LoginManager
import com.orogersilva.superpub.dublin.di.scopes.LoginScope
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * Created by orogersilva on 4/14/2017.
 */
@LoginScope
@Module
open class FacebookSdkModule {

    // region PROVIDERS

    @Provides @LoginScope open fun provideLoginManager(): LoginManager = LoginManager.getInstance()

    @Provides @LoginScope open fun provideCallbackManager(): CallbackManager = CallbackManager.Factory.create()

    // endregion
}