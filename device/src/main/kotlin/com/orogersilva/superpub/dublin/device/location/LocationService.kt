package com.orogersilva.superpub.dublin.device.location

import android.annotation.SuppressLint
import android.app.PendingIntent
import android.app.Service
import android.content.Intent
import android.os.Bundle
import android.os.IBinder
import android.os.Parcelable
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.common.api.Status
import com.google.android.gms.location.*
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import com.orogersilva.superpub.dublin.device.R
import com.orogersilva.superpub.dublin.domain.di.scope.ActivityScope
import java.io.Serializable
import java.lang.Exception

/**
 * Created by orogersilva on 7/4/2017.
 */
@ActivityScope
class LocationService : Service() {

    // region PROPERTIES

    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    private lateinit var locationRequest: LocationRequest

    private val LOCATION_REQUEST_INTERVAL = 5000L
    private val LOCATION_REQUEST_FASTEST_INTERVAL = 3000L
    private val LOCATION_REQUEST_PRIORITY = LocationRequest.PRIORITY_HIGH_ACCURACY

    private lateinit var locationPendingIntent: PendingIntent

    private val LOCATION_SEND_BROADCAST_REQUEST_CODE = 1

    // endregion

    // region OVERRIDED METHODS

    override fun onCreate() {

        super.onCreate()

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)

        locationRequest = createLocationRequest(LOCATION_REQUEST_INTERVAL,
                LOCATION_REQUEST_FASTEST_INTERVAL, LOCATION_REQUEST_PRIORITY)
    }

    override fun onBind(intent: Intent?): IBinder? = null

    @SuppressLint("MissingPermission")
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {

        val locationSettingsRequestBuilder = LocationSettingsRequest.Builder()
                .addLocationRequest(locationRequest)

        val settingsClient = LocationServices.getSettingsClient(this)

        val locationSettingsTask = settingsClient.checkLocationSettings(locationSettingsRequestBuilder.build())

        locationSettingsTask.addOnSuccessListener(object : OnSuccessListener<LocationSettingsResponse> {

            override fun onSuccess(locationSettingsResponse: LocationSettingsResponse?) {

                val locationIntent = Intent(getString(R.string.get_location_action))

                locationPendingIntent = PendingIntent.getBroadcast(baseContext, LOCATION_SEND_BROADCAST_REQUEST_CODE,
                        locationIntent, PendingIntent.FLAG_CANCEL_CURRENT)

                fusedLocationProviderClient.requestLocationUpdates(locationRequest, locationPendingIntent)
            }
        })

        locationSettingsTask.addOnFailureListener(object : OnFailureListener {

            override fun onFailure(exception: Exception) {

                val statusCode = (exception as ApiException).statusCode

                when (statusCode) {

                    LocationSettingsStatusCodes.RESOLUTION_REQUIRED -> {

                        val locationSettingsIntent = Intent(getString(R.string.set_location_settings_action))

                        val resolvableApiException = exception as ResolvableApiException

                        val locationSettingsExtras = Bundle()

                        locationSettingsExtras.putInt("locationSettingsFailureStatusCode", resolvableApiException.statusCode)
                        locationSettingsExtras.putString("locationSettingsFailureStatusMessage", resolvableApiException.statusMessage)
                        locationSettingsExtras.putParcelable("locationSettingsFailureResolution", resolvableApiException.resolution)

                        locationSettingsIntent.putExtras(locationSettingsExtras)

                        sendBroadcast(locationSettingsIntent)
                    }

                    LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE -> {

                        // TODO: To implement.
                    }
                }
            }
        })

        return START_STICKY
    }

    override fun onDestroy() {

        super.onDestroy()

        fusedLocationProviderClient.removeLocationUpdates(locationPendingIntent)
    }

    // endregion

    // region UTILITY METHODS

    private fun createLocationRequest(interval: Long, fastestInterval: Long, priority: Int): LocationRequest {

        val locationRequest = LocationRequest()

        locationRequest.interval = interval
        locationRequest.fastestInterval = fastestInterval
        locationRequest.priority = priority

        return locationRequest
    }

    // endregion
}