package com.orogersilva.superpub.dublin.device.location

import android.content.IntentSender
import android.location.Location
import android.os.Bundle
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.common.api.PendingResult
import com.google.android.gms.common.api.ResultCallback
import com.google.android.gms.location.*
import javax.inject.Inject

/**
 * Created by orogersilva on 5/26/2017.
 */
class LocationManager @Inject constructor(private val googleApiClient: GoogleApiClient,
                                          private val locationCallback: LocationCallback)
    : GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener,
        ResultCallback<LocationSettingsResult>, com.google.android.gms.location.LocationListener {

    // region PROPERTIES

    private var lastLocation: Location? = null

    private val locationRequest: LocationRequest
    private val locationSettingsRequestBuilder: LocationSettingsRequest.Builder
    private lateinit var pendingResult: PendingResult<LocationSettingsResult>

    private val LOCATION_REQUEST_INTERVAL = 30000L
    private val LOCATION_REQUEST_FASTEST_INTERVAL = 10000L
    private val LOCATION_REQUEST_PRIORITY = LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY

    // private val CHECK_SETTINGS_REQUEST_CODE = 1

    // endregion

    // region INITIALIZER BLOCK

    init {

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

            /*LocationSettingsStatusCodes.RESOLUTION_REQUIRED ->

                try {

                    status.startResolutionForResult(sourceActivity, CHECK_SETTINGS_REQUEST_CODE)

                } catch (e: IntentSender.SendIntentException) {
                }*/

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