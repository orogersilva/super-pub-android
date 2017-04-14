package com.orogersilva.superpub.dublin.di.modules

import com.facebook.CallbackManager
import com.facebook.login.LoginManager
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * Created by orogersilva on 4/14/2017.
 */
@Module
class FacebookSdkModule {

    // region PROVIDERS

    @Singleton @Provides open fun provideLoginManager(): LoginManager = LoginManager.getInstance()

    @Singleton @Provides open fun provideCallbackManager(): CallbackManager = CallbackManager.Factory.create()

    // endregion
}