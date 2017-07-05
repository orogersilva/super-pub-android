package com.orogersilva.superpub.dublin.device.location

import android.annotation.SuppressLint
import android.app.PendingIntent
import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices
import com.orogersilva.superpub.dublin.domain.di.scope.ActivityScope

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

        val locationIntent = Intent("com.orogersilva.superpub.dublin.device.location.LocationBroadcastReceiver")

        locationPendingIntent = PendingIntent.getBroadcast(this, LOCATION_SEND_BROADCAST_REQUEST_CODE,
                locationIntent, PendingIntent.FLAG_UPDATE_CURRENT)

        Log.d("LocationService", "Ready for requestLocationUpdates...")

        fusedLocationProviderClient.requestLocationUpdates(locationRequest, locationPendingIntent)

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