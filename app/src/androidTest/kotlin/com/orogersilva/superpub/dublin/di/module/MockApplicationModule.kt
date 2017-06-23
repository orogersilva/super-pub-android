package com.orogersilva.superpub.dublin.di.module

import android.content.Context
import com.nhaarman.mockito_kotlin.mock
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * Created by orogersilva on 6/20/2017.
 */
class MockApplicationModule(val contextMock: Context) : ApplicationModule(contextMock) {

    // region OVERRIDED METHODS

    override fun provideContext(): Context = contextMock

    // endregion
}