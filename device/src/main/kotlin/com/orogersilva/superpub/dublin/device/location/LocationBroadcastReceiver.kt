package com.orogersilva.superpub.dublin.device.location

import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.google.android.gms.common.api.Status
import com.google.android.gms.location.LocationResult
import com.orogersilva.superpub.dublin.device.R
import com.orogersilva.superpub.dublin.domain.di.scope.ActivityScope
import com.orogersilva.superpub.dublin.data.PreferencesDataSource
import javax.inject.Inject

/**
 * Created by orogersilva on 7/4/2017.
 */
@ActivityScope
class LocationBroadcastReceiver @Inject constructor(private val preferencesDataSource: PreferencesDataSource) : BroadcastReceiver() {

    // region OVERRIDED METHODS

    override fun onReceive(context: Context, intent: Intent) {

        val locationIntentAction = intent.action

        val GET_LOCATION_ACTION = context.getString(R.string.get_location_action)
        val SET_LOCATION_SETTINGS_ACTION = context.getString(R.string.set_location_settings_action)

        when (locationIntentAction) {

            GET_LOCATION_ACTION -> {

                if (LocationResult.hasResult(intent)) {

                    val location = LocationResult.extractResult(intent).lastLocation

                    location?.let {

                        preferencesDataSource.saveLocation(it.latitude, it.longitude)
                    }
                }
            }

            SET_LOCATION_SETTINGS_ACTION -> {

                val locationSettingsExtras = intent.extras

                val locationSettingsFailureStatusCode = locationSettingsExtras.getInt("locationSettingsFailureStatusCode")
                val locationSettingsFailureStatusMessage = locationSettingsExtras.getString("locationSettingsFailureStatusMessage")
                val locationSettingsResolution = locationSettingsExtras.getParcelable<PendingIntent>("locationSettingsFailureResolution")

                val locationSettingsFailureStatus = Status(locationSettingsFailureStatusCode, locationSettingsFailureStatusMessage, locationSettingsResolution)

                preferencesDataSource.setLocationSettingsFailureStatus(locationSettingsFailureStatus)
            }
        }
    }

    // endregion
}