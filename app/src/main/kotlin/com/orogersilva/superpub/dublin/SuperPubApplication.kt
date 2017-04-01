package com.orogersilva.superpub.dublin

import android.app.Application
import android.os.StrictMode

/**
 * Created by orogersilva on 3/31/2017.
 */
class SuperPubApplication : Application() {

    // region APPLICATION LIFECYCLE METHODS

    override fun onCreate() {

        super.onCreate()

        if (BuildConfig.DEBUG) {

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
        }
    }

    // endregion
}