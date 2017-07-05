package com.orogersilva.superpub.dublin.device.location

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import com.google.android.gms.location.LocationResult
import com.orogersilva.superpub.dublin.domain.di.scope.ActivityScope
import com.orogersilva.superpub.dublin.domain.local.PreferencesDataSource
import javax.inject.Inject

/**
 * Created by orogersilva on 7/4/2017.
 */
@ActivityScope
class LocationBroadcastReceiver @Inject constructor(private val preferencesDataSource: PreferencesDataSource) : BroadcastReceiver() {

    // region OVERRIDED METHODS

    override fun onReceive(context: Context?, intent: Intent?) {

        Log.d("LocBroadcastReceiver", "onReceive called!")

        if (LocationResult.hasResult(intent)) {

            val location = LocationResult.extractResult(intent).lastLocation

            location?.let {

                preferencesDataSource.saveLocation(it.latitude, it.longitude)
            }
        }
    }

    // endregion
}