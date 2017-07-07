package com.orogersilva.superpub.dublin

import android.app.Application
import android.content.Context
import android.os.StrictMode
import android.support.multidex.MultiDex
import com.facebook.stetho.Stetho
import com.orogersilva.superpub.dublin.di.component.ApplicationComponent
import com.orogersilva.superpub.dublin.di.component.DaggerApplicationComponent
import com.orogersilva.superpub.dublin.di.component.LoggedInComponent
import com.orogersilva.superpub.dublin.di.component.LoggedOutComponent
import com.orogersilva.superpub.dublin.di.module.*
import com.squareup.leakcanary.LeakCanary

/**
 * Created by orogersilva on 3/31/2017.
 */
open class SuperPubApplication : Application() {

    // region PROPERTIES

    lateinit var applicationComponent: ApplicationComponent

    // var loggedOutComponent: LoggedOutComponent? = null
    var loggedInComponent: LoggedInComponent? = null

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

        applicationComponent = createApplicationComponent()
    }

    // endregion

    // region PUBLIC METHODS

    open fun createApplicationComponent(): ApplicationComponent =
            DaggerApplicationComponent.builder()
                    .applicationModule(ApplicationModule(this))
                    .facebookSdkModule(FacebookSdkModule())
                    .build()

    /*fun createLoggedOutComponent(): LoggedOutComponent? {

        if (loggedOutComponent == null) {

            loggedOutComponent = applicationComponent
                    .newLoggedOutComponent(FacebookAdapterServiceModule())
        }

        return loggedOutComponent
    }*/

    fun createLoggedInComponent(provideRealmInstance: Boolean): LoggedInComponent? {

        if (loggedInComponent == null) {

            loggedInComponent = applicationComponent
                    .newLoggedinComponent(CacheModule(), DatabaseModule(provideRealmInstance),
                            GoogleApiModule(), NetworkModule(), PreferencesModule(),
                            SchedulerProviderModule())
        }

        return loggedInComponent
    }

    // endregion
}