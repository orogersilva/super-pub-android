package com.orogersilva.superpub.dublin

import com.orogersilva.superpub.dublin.di.component.ApplicationComponent
import com.orogersilva.superpub.dublin.di.component.DaggerApplicationComponent
import com.orogersilva.superpub.dublin.di.module.ApplicationModule
import com.orogersilva.superpub.dublin.di.module.FacebookSdkModule

/**
 * Created by orogersilva on 6/20/2017.
 */
class CustomInstrumentationTestApplication : SuperPubApplication() {

    // region OVERRIDED METHODS

    override fun createApplicationComponent(): ApplicationComponent {

        applicationComponent = DaggerApplicationComponent.builder()
                .applicationModule(ApplicationModule(this))
                .facebookSdkModule(FacebookSdkModule())
                .build()

        return applicationComponent
    }

    // endregion
}