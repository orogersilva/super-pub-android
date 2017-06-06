package com.orogersilva.superpub.dublin.di.module

import com.facebook.CallbackManager
import com.facebook.login.LoginManager
import com.orogersilva.superpub.dublin.domain.di.qualifier.AccessToken
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * Created by orogersilva on 4/14/2017.
 */
@Singleton
@Module
open class FacebookSdkModule {

    // region PROVIDERS

    @Provides @Singleton open fun provideLoginManager(): LoginManager = LoginManager.getInstance()

    @Provides @Singleton open fun provideCallbackManager(): CallbackManager = CallbackManager.Factory.create()

    @Provides @Singleton @AccessToken open fun provideAccessToken(): String = com.facebook.AccessToken.getCurrentAccessToken().token

    // endregion
}