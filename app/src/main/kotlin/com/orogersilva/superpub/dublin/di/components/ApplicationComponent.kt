package com.orogersilva.superpub.dublin.di.components

import com.orogersilva.superpub.dublin.di.modules.*
import dagger.Component
import javax.inject.Singleton

/**
 * Created by orogersilva on 4/17/2017.
 */
@Singleton
@Component(modules = arrayOf(ApplicationModule::class))
interface ApplicationComponent {

    // region FACTORY METHODS

    fun newLoginComponent(loginPresenterModule: LoginPresenterModule,
                          facebookSdkModule: FacebookSdkModule): LoginComponent
    fun newPubRepositoryComponent(databaseModule: DatabaseModule,
                                  networkModule: NetworkModule): PubRepositoryComponent

    // endregion
}