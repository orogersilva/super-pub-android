package com.orogersilva.superpub.dublin

import android.content.IntentSender
import android.location.Location
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.common.api.PendingResult
import com.google.android.gms.common.api.ResultCallback
import com.google.android.gms.location.*
import javax.inject.Inject

/**
 * Created by orogersilva on 5/22/2017.
 */
class LocationManager @Inject constructor(private val sourceActivity: AppCompatActivity,
                                          private val googleApiClient: GoogleApiClient,
                                          private val locationCallback: com.orogersilva.superpub.dublin.LocationCallback)
    : GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener,
        ResultCallback<LocationSettingsResult>, com.google.android.gms.location.LocationListener {

    // region PROPERTIES

    private val locationRequest: LocationRequest
    private val locationSettingsRequestBuilder: LocationSettingsRequest.Builder
    private lateinit var pendingResult: PendingResult<LocationSettingsResult>

    private val LOCATION_REQUEST_INTERVAL = 30000L
    private val LOCATION_REQUEST_FASTEST_INTERVAL = 10000L
    private val LOCATION_REQUEST_PRIORITY = LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY

    private var lastLocation: Location? = null

    // private val ACCESS_LOCATION_PERMISSION_REQUEST_CODE = 1
    private val CHECK_SETTINGS_REQUEST_CODE = 2

    // endregion

    // region INITIALIZER BLOCK

    init {

        /*googleApiClient = GoogleApiClient.Builder(context)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build()*/

        locationRequest = createLocationRequest(LOCATION_REQUEST_INTERVAL,
                LOCATION_REQUEST_FASTEST_INTERVAL, LOCATION_REQUEST_PRIORITY)
        locationSettingsRequestBuilder = LocationSettingsRequest.Builder()
                .addLocationRequest(locationRequest)
    }

    // endregion

    // region PUBLIC METHODS

    fun connect() {

        googleApiClient.connect()
    }

    fun disconnect() {

        googleApiClient.disconnect()

        stopLocationUpdates()
    }

    fun isConnectionEstablished(): Boolean = googleApiClient.isConnected

    // endregion

    // region OVERRIDED METHODS

    override fun onConnected(bundle: Bundle?) {

        // TODO: MOVE THIS COMMENTED CODE SNIPPET TO AN ACTIVITY.

        /*if ((ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) &&
                (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION)
                        != PackageManager.PERMISSION_GRANTED)) {

            if (ActivityCompat.shouldShowRequestPermissionRationale((context as AppCompatActivity),
                    Manifest.permission.ACCESS_COARSE_LOCATION) ||
                    (ActivityCompat.shouldShowRequestPermissionRationale(context,
                            Manifest.permission.ACCESS_FINE_LOCATION))) {

                // TODO: SHOULD BE IMPLEMENTED EXPLANATION TO THE USE ABOUT WHY TO USE LOCATION FROM DEVICE.

            } else {

                ActivityCompat.requestPermissions(context,
                        arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION,
                                Manifest.permission.ACCESS_FINE_LOCATION),
                        ACCESS_LOCATION_PERMISSION_REQUEST_CODE)
            }

            return
        }*/

        pendingResult = LocationServices.SettingsApi.checkLocationSettings(
                googleApiClient, locationSettingsRequestBuilder.build())

        lastLocation = LocationServices.FusedLocationApi.getLastLocation(googleApiClient)

        startLocationUpdates()

        locationCallback.onLocationManagerConnected()
    }

    override fun onConnectionSuspended(i: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onConnectionFailed(connectionResult: ConnectionResult) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onResult(locationSettingsResult: LocationSettingsResult) {

        val status = locationSettingsResult.status

        when (status.statusCode) {

            LocationSettingsStatusCodes.RESOLUTION_REQUIRED ->

                    try {

                        status.startResolutionForResult(sourceActivity, CHECK_SETTINGS_REQUEST_CODE)

                    } catch (e: IntentSender.SendIntentException) {
                    }

            LocationSettingsStatusCodes.SUCCESS, LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE -> {}
        }
    }

    override fun onLocationChanged(newLocation: Location?) {

        lastLocation = newLocation

        locationCallback.onLocationChanged(newLocation)
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

    private fun startLocationUpdates() {

        LocationServices.FusedLocationApi.requestLocationUpdates(googleApiClient, locationRequest, this)
    }

    internal fun stopLocationUpdates() {

        LocationServices.FusedLocationApi.removeLocationUpdates(googleApiClient, this)
    }

    // endregion
}