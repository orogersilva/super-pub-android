package com.orogersilva.superpub.dublin

import android.app.Application
import android.os.StrictMode
import com.facebook.FacebookSdk
import com.facebook.stetho.Stetho
import com.orogersilva.superpub.dublin.di.components.ApplicationComponent
import com.orogersilva.superpub.dublin.di.components.DaggerApplicationComponent
import com.squareup.leakcanary.LeakCanary

/**
 * Created by orogersilva on 3/31/2017.
 */
class SuperPubApplication : Application() {

    // region PROPERTIES

    lateinit var applicationComponent: ApplicationComponent

    // endregion

    // region APPLICATION LIFECYCLE METHODS

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
                .build()
    }

    // endregion
}