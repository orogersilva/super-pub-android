package com.orogersilva.superpub.dublin.di.modules

import android.app.Application
import android.content.Context
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * Created by orogersilva on 4/30/2017.
 */
@Singleton
@Module
open class ApplicationModule(private val context: Context?) {

    @Provides @Singleton open fun provideContext(): Context? = context
}