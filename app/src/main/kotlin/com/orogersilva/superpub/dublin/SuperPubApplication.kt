package com.orogersilva.superpub.dublin

import android.app.Application
import android.content.Context
import android.os.StrictMode
import android.support.multidex.MultiDex
import com.facebook.stetho.Stetho
import com.orogersilva.superpub.dublin.di.component.ApplicationComponent
import com.orogersilva.superpub.dublin.di.component.DaggerApplicationComponent
import com.orogersilva.superpub.dublin.di.module.ApplicationModule
import com.orogersilva.superpub.dublin.di.module.FacebookSdkModule
import com.squareup.leakcanary.LeakCanary

/**
 * Created by orogersilva on 3/31/2017.
 */
class SuperPubApplication : Application() {

    // region PROPERTIES

    lateinit var applicationComponent: ApplicationComponent

    // endregion

    // region APPLICATION LIFECYCLE METHODS

    override fun attachBaseContext(base: Context?) {

        super.attachBaseContext(base)

        MultiDex.install(this)
    }

    override fun onCreate() {

        super.onCreate()

        if (BuildConfig.DEBUG) {

            if (LeakCanary.isInAnalyzerProcess(this)) return

            LeakCanary.install(this)

            StrictMode.setThreadPolicy(StrictMode.ThreadPolicy.Builder()
                    .detectAll()
                    .penaltyLog()
                    .build()
            )

            StrictMode.setVmPolicy(StrictMode.VmPolicy.Builder()
                    .detectAll()
                    .penaltyLog()
                    .build()
            )

            Stetho.initializeWithDefaults(this)
        }

        applicationComponent = DaggerApplicationComponent.builder()
                .applicationModule(ApplicationModule(this))
                .facebookSdkModule(FacebookSdkModule())
                .build()
    }

    // endregion

    // region PUBLIC METHODS

    /*@VisibleForTesting fun setTestComponent(applicationComponent: ApplicationComponent) {

        this.applicationComponent = applicationComponent
    }*/

    // endregion
}