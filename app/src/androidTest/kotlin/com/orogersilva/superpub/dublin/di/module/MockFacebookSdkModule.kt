package com.orogersilva.superpub.dublin.di.module

import com.facebook.CallbackManager
import com.facebook.login.LoginManager

/**
 * Created by orogersilva on 6/20/2017.
 */
class MockFacebookSdkModule(val loginManagerMock: LoginManager,
                            val callbackManagerMock: CallbackManager,
                            val accessTokenMock: String) : FacebookSdkModule() {

    // region OVERRIDED METHODS

    override fun provideLoginManager(): LoginManager = loginManagerMock

    override fun provideCallbackManager(): CallbackManager = callbackManagerMock

    override fun provideAccessToken(): String = accessTokenMock

    // endregion
}