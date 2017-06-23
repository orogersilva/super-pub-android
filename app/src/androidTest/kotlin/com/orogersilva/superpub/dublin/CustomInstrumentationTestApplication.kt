package com.orogersilva.superpub.dublin

import com.facebook.CallbackManager
import com.facebook.login.LoginManager
import com.orogersilva.superpub.dublin.di.component.ApplicationComponent
import com.orogersilva.superpub.dublin.di.component.DaggerApplicationComponent
import com.orogersilva.superpub.dublin.di.module.MockApplicationModule
import com.orogersilva.superpub.dublin.di.module.MockFacebookSdkModule

/**
 * Created by orogersilva on 6/20/2017.
 */
class CustomInstrumentationTestApplication : SuperPubApplication() {

    // region PUBLIC METHODS

    fun createApplicationComponent(loginManagerMock: LoginManager,
                                            callbackManagerMock: CallbackManager,
                                            accessTokenMock: String): ApplicationComponent {

        applicationComponent = DaggerApplicationComponent.builder()
                .applicationModule(MockApplicationModule(this))
                .facebookSdkModule(MockFacebookSdkModule(loginManagerMock, callbackManagerMock, accessTokenMock))
                .build()

        return applicationComponent
    }

    // endregion
}